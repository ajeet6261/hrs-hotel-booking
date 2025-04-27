package com.hrs.hotelbooking.service;

import java.util.List;

import com.hrs.hotelbooking.model.Booking;
import com.hrs.hotelbooking.model.BookingHistory;
import com.hrs.hotelbooking.model.ServiceContext;

public interface GetBookingService {
    Booking getBooking(String bookingId, ServiceContext serviceContext);

    List<BookingHistory> getAllBookings(ServiceContext serviceContext);
} 