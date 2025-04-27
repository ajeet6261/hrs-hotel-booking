package com.hrs.hotelbooking.service;

import com.hrs.hotelbooking.model.CancellationRequest;
import com.hrs.hotelbooking.model.Response;
import com.hrs.hotelbooking.model.ServiceContext;

public interface CancelBookingService {
    Response cancelBooking(CancellationRequest cancellationRequest, ServiceContext serviceContext);
} 