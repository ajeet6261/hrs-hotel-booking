package com.hrs.hotelbooking.service.impl;

import com.hrs.hotelbooking.adapter.CancellationPolicyAdapter;
import com.hrs.hotelbooking.exception.InvalidRequestException;
import com.hrs.hotelbooking.model.*;
import com.hrs.hotelbooking.repository.CancellationDetailsRepository;
import com.hrs.hotelbooking.repository.HotelDetailsRepository;
import com.hrs.hotelbooking.service.GetBookingService;
import com.hrs.hotelbooking.utils.CacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
public class GetBookingServiceImpl implements GetBookingService {
    
    private static final Logger logger = LoggerFactory.getLogger(GetBookingServiceImpl.class);
    
    @Autowired
    private HotelDetailsRepository hotelDetailsRepository;

    @Autowired
    private CancellationPolicyAdapter cancellationPolicyAdapter;

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private CancellationDetailsRepository cancellationDetailsRepository;


    @Override
    public CompletableFuture<Booking> getBooking(String bookingId, ServiceContext serviceContext) {
        logger.info("Fetching booking details for bookingId: {}", bookingId);
        
        return CompletableFuture.supplyAsync(() -> {
            // First try to get from cache
            List<HotelDetails> hotelDetails = CacheUtil.getHotelDetailsFromCache(bookingId);
            logger.debug("Cache lookup result for bookingId {}: {}", bookingId, hotelDetails != null ? "Found" : "Not found");

            // If not in cache, fetch from database
            if (hotelDetails == null || hotelDetails.isEmpty()) {
                logger.info("Cache miss for bookingId: {}, fetching from database", bookingId);
                List<HotelDetails> dbHotelDetails = hotelDetailsRepository.findByBookingId(bookingId);
                if (dbHotelDetails == null || dbHotelDetails.isEmpty()) {
                    logger.error("Booking not found in database for bookingId: {}", bookingId);
                    throw new InvalidRequestException("Booking not found");
                }
                // Update cache with the fetched data asynchronously
                final List<HotelDetails> finalHotelDetails = dbHotelDetails;
                CompletableFuture.runAsync(() -> {
                    logger.debug("Updating cache with hotel details for bookingId: {}", bookingId);
                    CacheUtil.addHotelDetailsToCache(bookingId, finalHotelDetails);
                }, executorService);
                hotelDetails = dbHotelDetails;
            }

            // Get cancellation policies
            List<CancellationPolicy> cancellationPolicies = CacheUtil.getCancellationPoliciesFromCache(bookingId);
            if (cancellationPolicies == null) {
                logger.info("Fetching cancellation policies from third-party API for bookingId: {}", bookingId);
                // If not in cache, fetch from third-party API
                try {
                    List<CancellationPolicy> apiPolicies = cancellationPolicyAdapter.getCancellationPolicies(bookingId).get();
                    // Cache the policies asynchronously
                    final List<CancellationPolicy> finalPolicies = apiPolicies;
                    CompletableFuture.runAsync(() -> {
                        logger.debug("Caching cancellation policies for bookingId: {}", bookingId);
                        CacheUtil.addCancellationPoliciesToCache(bookingId, finalPolicies);
                    }, executorService);
                    cancellationPolicies = apiPolicies;
                } catch (Exception e) {
                    logger.error("Error fetching cancellation policies for bookingId: {}", bookingId, e);
                    throw new RuntimeException("Error fetching cancellation policies", e);
                }
            }
            List<CancellationDetails> cancellationDetails = cancellationDetailsRepository.findByBookingId(bookingId);
            Booking booking = new Booking();
            booking.setBookingId(bookingId);
            booking.setHotelDetails(hotelDetails);
            booking.setHotel(booking.getHotel());
            booking.setCancellationPolicies(cancellationPolicies);
            booking.setCancellationDetails(cancellationDetails);
            logger.info("Successfully retrieved booking details for bookingId: {}", bookingId);
            return booking;
        }, executorService);
    }

    @Override
    public CompletableFuture<List<BookingHistory>> getAllBookings(ServiceContext serviceContext) {
        String userId = serviceContext.getBooking().getUser().getUserId();
        logger.info("Fetching all bookings for userId: {}", userId);
        
        return CompletableFuture.supplyAsync(() -> {
            List<BookingHistory> histories = CacheUtil.getBookingHistory(userId);
            logger.debug("Found {} bookings for userId: {}", histories.size(), userId);
            return histories;
        }, executorService).thenApply(histories -> {
            logger.info("Successfully retrieved all bookings for userId: {}", userId);
            return histories;
        }).exceptionally(ex -> {
            logger.error("Error retrieving all bookings for userId: {}", userId, ex);
            throw new RuntimeException("Error retrieving all bookings", ex);
        });
    }
} 