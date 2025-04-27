package com.hrs.hotelbooking.service.impl;

import com.hrs.hotelbooking.dao.HotelDetailsDao;
import com.hrs.hotelbooking.exception.InvalidRequestException;
import com.hrs.hotelbooking.model.*;
import com.hrs.hotelbooking.repository.HotelRepository;
import com.hrs.hotelbooking.repository.UserRepository;
import com.hrs.hotelbooking.service.CreateBookingService;
import com.hrs.hotelbooking.utils.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
public class CreateBookingServiceImpl implements CreateBookingService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelDetailsDao hotelDetailsDao;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExecutorService executorService;

    private User validateUser(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new InvalidRequestException("User ID is required");
        }

        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new InvalidRequestException("Invalid user ID");
        }

        if (!user.isActive()) {
            throw new InvalidRequestException("User account is not active");
        }

        return user;
    }

    @Override
    public Response createBooking(BookingRequest bookingRequest, ServiceContext serviceContext) {
        Response response = new Response();

        // Validate user
        User user = validateUser(bookingRequest.getUserId());

        Optional<Hotel> hotelOptional = hotelRepository.findById(bookingRequest.getHotelCode());
        if (hotelOptional.isPresent()) {
            Hotel hotel = hotelOptional.get();
            if (hotel.getStatus() != 1) {
                throw new InvalidRequestException("Hotel is not active");
            }
            List<HotelDetails> hotelDetails = hotelDetailsDao.prepareHotelDetails(hotel, bookingRequest);
            hotelDetailsDao.saveAll(hotelDetails);

            // Cache hotel details and publish to queue asynchronously
            String bookingId = hotelDetails.get(0).getBookingId();
            response.setBookingId(bookingId);
            bookingRequest.setBookingId(bookingId);
            CompletableFuture.runAsync(() -> {
                CacheUtil.addHotelDetailsToCache(bookingId, hotelDetails);
                CacheUtil.publishPacketToQueue(bookingRequest);
            }, executorService);
        } else {
            throw new InvalidRequestException("Hotel is not registered");
        }
        response.setStatus(true);
        response.setMessage("Booking created successfully");
        response.setUserId(user.getUserId());
        return response;
    }
} 