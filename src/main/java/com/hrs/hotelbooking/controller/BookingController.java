package com.hrs.hotelbooking.controller;

import com.hrs.hotelbooking.model.Booking;
import com.hrs.hotelbooking.model.Response;
import com.hrs.hotelbooking.service.impl.BookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    private BookingServiceImpl bookingService;

    @PostMapping("/createBooking")
    public Response createBooking(@RequestBody Booking booking) {
        return bookingService.createBooking(booking);
    }

    @GetMapping("/getBooking/{bookingId}")
    public Booking getBooking(@PathVariable String bookingId) {
        return bookingService.getBooking(bookingId);
    }

    @PutMapping("/updateBooking")
    public Response updateBooking(@RequestBody Booking booking) {
        return bookingService.updateBooking(booking);
    }

    @DeleteMapping("/cancelBooking/{bookingId}")
    public Response cancelBooking(@PathVariable String bookingId) {
        return bookingService.cancelBooking(bookingId);
    }
}
