package com.hrs.hotelbooking.service;

import com.hrs.hotelbooking.model.Booking;
import com.hrs.hotelbooking.model.Response;

public interface BookingService {
    Response createBooking(Booking booking);

    Booking getBooking(String bookingId);

    Response updateBooking(Booking booking);

    Response cancelBooking(String bookingId);
} 