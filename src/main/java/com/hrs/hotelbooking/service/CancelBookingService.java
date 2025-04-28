package com.hrs.hotelbooking.service;

import com.hrs.hotelbooking.model.CancellationRequest;
import com.hrs.hotelbooking.model.Response;
import com.hrs.hotelbooking.model.ServiceContext;

import java.util.concurrent.CompletableFuture;

public interface CancelBookingService {
    CompletableFuture<Response> cancelBooking(CancellationRequest cancellationRequest, ServiceContext serviceContext);
} 