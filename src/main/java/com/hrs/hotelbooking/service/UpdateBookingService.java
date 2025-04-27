package com.hrs.hotelbooking.service;

import com.hrs.hotelbooking.model.Booking;
import com.hrs.hotelbooking.model.Response;
import com.hrs.hotelbooking.model.ServiceContext;

public interface UpdateBookingService {
    Response updateBooking(Booking booking, ServiceContext serviceContext);
} 