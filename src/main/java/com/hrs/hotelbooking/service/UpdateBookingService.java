package com.hrs.hotelbooking.service;

import com.hrs.hotelbooking.model.Booking;
import com.hrs.hotelbooking.model.Response;
import com.hrs.hotelbooking.model.ServiceContext;

import java.util.concurrent.CompletableFuture;

public interface UpdateBookingService {
    CompletableFuture<Response> updateBooking(Booking booking, ServiceContext serviceContext);
} 