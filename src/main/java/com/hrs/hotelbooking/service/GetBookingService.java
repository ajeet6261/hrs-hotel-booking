package com.hrs.hotelbooking.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.hrs.hotelbooking.model.Booking;
import com.hrs.hotelbooking.model.BookingHistory;
import com.hrs.hotelbooking.model.ServiceContext;

public interface GetBookingService {
    CompletableFuture<Booking> getBooking(String bookingId, ServiceContext serviceContext);

    CompletableFuture<List<BookingHistory>> getAllBookings(ServiceContext serviceContext);
} 