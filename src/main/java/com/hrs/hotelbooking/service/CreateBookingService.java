package com.hrs.hotelbooking.service;

import com.hrs.hotelbooking.model.BookingRequest;
import com.hrs.hotelbooking.model.Response;
import com.hrs.hotelbooking.model.ServiceContext;

import java.util.concurrent.CompletableFuture;

public interface CreateBookingService {
    CompletableFuture<Response> createBooking(BookingRequest bookingRequest, ServiceContext serviceContext);
} 