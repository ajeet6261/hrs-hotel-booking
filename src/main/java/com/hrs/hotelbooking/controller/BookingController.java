package com.hrs.hotelbooking.controller;

import com.hrs.hotelbooking.model.*;
import com.hrs.hotelbooking.service.CancelBookingService;
import com.hrs.hotelbooking.service.CreateBookingService;
import com.hrs.hotelbooking.service.GetBookingService;
import com.hrs.hotelbooking.service.UpdateBookingService;
import com.hrs.hotelbooking.service.impl.ServiceContextServiceImpl;
import com.hrs.hotelbooking.service.impl.UpdateBookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    private CreateBookingService createBookingService;

    @Autowired
    private GetBookingService getBookingService;

    @Autowired
    private UpdateBookingServiceImpl updateBookingService;

    @Autowired
    private CancelBookingService cancelBookingService;

    @Autowired
    private ServiceContextServiceImpl serviceContextService;


    @PostMapping("/createBooking")
    public Response createBooking(@RequestBody BookingRequest bookingRequest) {
        ServiceContext serviceContext = serviceContextService.createServiceContext(bookingRequest.getBookingId(), bookingRequest.getHotelCode(), bookingRequest.getUserId());
        return createBookingService.createBooking(bookingRequest, serviceContext);
    }

    @GetMapping("/getBooking/{bookingId}")
    public Booking getBooking(@PathVariable String bookingId) {
        ServiceContext serviceContext = serviceContextService.createServiceContext(bookingId, null, null);
        return getBookingService.getBooking(bookingId, serviceContext);
    }

    @GetMapping("/getAllBookings/{userId}")
    public List<BookingHistory> getAllBookings(@PathVariable String userId) {
        ServiceContext serviceContext = serviceContextService.createServiceContext(null, null, userId);
        return getBookingService.getAllBookings(serviceContext);
    }

    @PutMapping("/updateBooking")
    public Response updateBooking(@RequestBody Booking booking) {
        ServiceContext serviceContext = serviceContextService.createServiceContext(booking.getBookingId(), null, null);
        return updateBookingService.updateBooking(booking, serviceContext);
    }

    @PostMapping("/cancelBooking")
    public Response cancelBooking(@RequestBody CancellationRequest cancellationRequest) {
        ServiceContext serviceContext = serviceContextService.createServiceContext(cancellationRequest.getBookingId(), null, cancellationRequest.getUserId());
        return cancelBookingService.cancelBooking(cancellationRequest, serviceContext);
    }
}
