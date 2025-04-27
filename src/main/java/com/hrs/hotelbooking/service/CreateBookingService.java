package com.hrs.hotelbooking.service;

import com.hrs.hotelbooking.model.BookingRequest;
import com.hrs.hotelbooking.model.Response;
import com.hrs.hotelbooking.model.ServiceContext;

public interface CreateBookingService {
    Response createBooking(BookingRequest bookingRequest, ServiceContext serviceContext);
} 