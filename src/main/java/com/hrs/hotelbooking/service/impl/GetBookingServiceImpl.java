package com.hrs.hotelbooking.service.impl;

import com.hrs.hotelbooking.adapter.CancellationPolicyAdapter;
import com.hrs.hotelbooking.exception.InvalidRequestException;
import com.hrs.hotelbooking.model.*;
import com.hrs.hotelbooking.repository.HotelDetailsRepository;
import com.hrs.hotelbooking.service.GetBookingService;
import com.hrs.hotelbooking.utils.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
public class GetBookingServiceImpl implements GetBookingService {

    @Autowired
    private HotelDetailsRepository hotelDetailsRepository;

    @Autowired
    private CancellationPolicyAdapter cancellationPolicyAdapter;

    @Autowired
    private ExecutorService executorService;

    @Override
    public Booking getBooking(String bookingId, ServiceContext serviceContext) {
        // First try to get from cache
        List<HotelDetails> hotelDetails = CacheUtil.getHotelDetailsFromCache(bookingId);

        // If not in cache, fetch from database
        if (hotelDetails == null || hotelDetails.isEmpty()) {
            List<HotelDetails> dbHotelDetails = hotelDetailsRepository.findByBookingId(bookingId);
            if (dbHotelDetails == null || dbHotelDetails.isEmpty()) {
                throw new InvalidRequestException("Booking not found");
            }
            // Update cache with the fetched data asynchronously
            final List<HotelDetails> finalHotelDetails = dbHotelDetails;
            CompletableFuture.runAsync(() ->
                            CacheUtil.addHotelDetailsToCache(bookingId, finalHotelDetails),
                    executorService
            );
            hotelDetails = dbHotelDetails;
        }

        // Get cancellation policies
        List<CancellationPolicy> cancellationPolicies = CacheUtil.getCancellationPoliciesFromCache(bookingId);
        if (cancellationPolicies == null) {
            // If not in cache, fetch from third-party API
            CompletableFuture<List<CancellationPolicy>> futurePolicies =
                    cancellationPolicyAdapter.getCancellationPolicies(bookingId);
            try {
                List<CancellationPolicy> apiPolicies = futurePolicies.get();
                // Cache the policies asynchronously
                final List<CancellationPolicy> finalPolicies = apiPolicies;
                CompletableFuture.runAsync(() ->
                                CacheUtil.addCancellationPoliciesToCache(bookingId, finalPolicies),
                        executorService
                );
                cancellationPolicies = apiPolicies;
            } catch (Exception e) {
                throw new RuntimeException("Error fetching cancellation policies", e);
            }
        }

        Booking booking = new Booking();
        booking.setBookingId(bookingId);
        booking.setHotelDetails(hotelDetails);
        booking.setHotel(booking.getHotel());
        booking.setCancellationPolicies(cancellationPolicies);
        return booking;
    }

    @Override
    public List<BookingHistory> getAllBookings(ServiceContext serviceContext) {
        return CacheUtil.getBookingHistory(serviceContext.getBooking().getUser().getUserId());
    }
} 