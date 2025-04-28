package com.hrs.hotelbooking.controller;

import com.hrs.hotelbooking.model.*;
import com.hrs.hotelbooking.service.CancelBookingService;
import com.hrs.hotelbooking.service.CreateBookingService;
import com.hrs.hotelbooking.service.GetBookingService;
import com.hrs.hotelbooking.service.impl.ServiceContextServiceImpl;
import com.hrs.hotelbooking.service.impl.UpdateBookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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
    @Async("taskExecutor")
    public CompletableFuture<Response> createBooking(@RequestBody BookingRequest bookingRequest) {
        ServiceContext serviceContext = serviceContextService.createServiceContext(bookingRequest.getBookingId(), bookingRequest.getHotelCode(), bookingRequest.getUserId());
        return createBookingService.createBooking(bookingRequest, serviceContext);
    }

    @GetMapping("/getBooking/{bookingId}")
    @Async("taskExecutor")
    public CompletableFuture<Booking> getBooking(@PathVariable String bookingId) {
        ServiceContext serviceContext = serviceContextService.createServiceContext(bookingId, null, null);
        return getBookingService.getBooking(bookingId, serviceContext);
    }

    @GetMapping("/getAllBookings/{userId}")
    @Async("taskExecutor")
    public CompletableFuture<List<BookingHistory>> getAllBookings(@PathVariable String userId) {
        ServiceContext serviceContext = serviceContextService.createServiceContext(null, null, userId);
        return getBookingService.getAllBookings(serviceContext);
    }

    @PutMapping("/updateBooking")
    @Async("taskExecutor")
    public CompletableFuture<Response> updateBooking(@RequestBody BookingRequest bookingRequest) {
        ServiceContext serviceContext = serviceContextService.createServiceContext(bookingRequest.getBookingId(), bookingRequest.getHotelCode(), bookingRequest.getUserId());
        return updateBookingService.updateBooking(bookingRequest, serviceContext);
    }

    @PostMapping("/cancelBooking")
    @Async("taskExecutor")
    public CompletableFuture<Response> cancelBooking(@RequestBody CancellationRequest cancellationRequest) {
        ServiceContext serviceContext = serviceContextService.createServiceContext(cancellationRequest.getBookingId(), null, cancellationRequest.getUserId());
        return cancelBookingService.cancelBooking(cancellationRequest, serviceContext);
    }
}
