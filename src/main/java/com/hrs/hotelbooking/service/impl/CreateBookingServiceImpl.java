package com.hrs.hotelbooking.service.impl;

import com.hrs.hotelbooking.adapter.HotelierAdapter;
import com.hrs.hotelbooking.dao.HotelDetailsDao;
import com.hrs.hotelbooking.exception.InvalidRequestException;
import com.hrs.hotelbooking.model.*;
import com.hrs.hotelbooking.repository.HotelRepository;
import com.hrs.hotelbooking.repository.UserRepository;
import com.hrs.hotelbooking.service.CreateBookingService;
import com.hrs.hotelbooking.utils.CacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
public class CreateBookingServiceImpl implements CreateBookingService {

    private static final Logger logger = LoggerFactory.getLogger(CreateBookingServiceImpl.class);

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelDetailsDao hotelDetailsDao;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private HotelierAdapter hotelAdapter;

    private User validateUser(String userId) {
        logger.debug("Validating user with userId: {}", userId);
        if (userId == null || userId.trim().isEmpty()) {
            logger.error("User ID is required");
            throw new InvalidRequestException("User ID is required");
        }

        User user = userRepository.findByUserId(userId);
        if (user == null) {
            logger.error("Invalid user ID: {}", userId);
            throw new InvalidRequestException("Invalid user ID");
        }

        if (!user.isActive()) {
            logger.error("User account is not active for userId: {}", userId);
            throw new InvalidRequestException("User account is not active");
        }

        logger.debug("User validation successful for userId: {}", userId);
        return user;
    }

    @Override
    public CompletableFuture<Response> createBooking(BookingRequest bookingRequest, ServiceContext serviceContext) {
        logger.info("Creating booking for userId: {} and hotelCode: {}",
                bookingRequest.getUserId(), bookingRequest.getHotelCode());

        return CompletableFuture.supplyAsync(() -> {
            Response response = new Response();

            // Validate user
            User user = validateUser(bookingRequest.getUserId());

            Optional<Hotel> hotelOptional = hotelRepository.findById(bookingRequest.getHotelCode());
            if (hotelOptional.isPresent()) {
                Hotel hotel = hotelOptional.get();
                if (hotel.getStatus() != 1) {
                    logger.error("Hotel is not active for hotelCode: {}", hotel.getCode());
                    throw new InvalidRequestException("Hotel is not active");
                }
                // confirm booking at hotel end.
                HotelBookResponse hotelBookResponse = hotelAdapter.bookHotel(bookingRequest).join();
                if (hotelBookResponse.getStatus().equals("SUCCESS")) {
                    logger.debug("Booking confirmed at hotel end for bookingId: {}", bookingRequest.getBookingId());
                } else {
                    logger.error("Booking failed at hotel end for bookingId: {}", bookingRequest.getBookingId());
                    throw new InvalidRequestException("Booking failed at hotel end");
                }

                logger.debug("Creating hotel details for booking");
                List<HotelDetails> hotelDetails = hotelDetailsDao.prepareHotelDetails(hotel, bookingRequest);
                hotelDetailsDao.saveAll(hotelDetails);

                // Cache hotel details and publish to queue asynchronously
                String bookingId = hotelDetails.get(0).getBookingId();
                response.setBookingId(bookingId);
                bookingRequest.setBookingId(bookingId);

                logger.debug("Initiating async operations for bookingId: {}", bookingId);
                CompletableFuture.runAsync(() -> {
                    logger.debug("Caching hotel details and publishing to queue for bookingId: {}", bookingId);
                    CacheUtil.addHotelDetailsToCache(bookingId, hotelDetails);
                    CacheUtil.publishPacketToQueue(bookingRequest);
                }, executorService);
            } else {
                logger.error("Hotel not found for hotelCode: {}", bookingRequest.getHotelCode());
                throw new InvalidRequestException("Hotel is not registered");
            }

            response.setStatus(true);
            response.setMessage("Booking created successfully");
            response.setUserId(user.getUserId());
            logger.info("Booking created successfully for bookingId: {}", response.getBookingId());
            return response;
        }, executorService);
    }
} 