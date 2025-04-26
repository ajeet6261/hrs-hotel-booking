package com.hrs.hotelbooking.service;

import com.hrs.hotelbooking.model.Booking;
import com.hrs.hotelbooking.model.Response;
import java.util.List;

public interface BookingService {
    Response createBooking(Booking booking);
    Booking getBooking(String bookingId);
    Response updateBooking(Booking booking);
    Response cancelBooking(String bookingId);
} 