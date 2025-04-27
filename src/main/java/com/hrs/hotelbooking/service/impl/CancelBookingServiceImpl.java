package com.hrs.hotelbooking.service.impl;

import com.hrs.hotelbooking.adapter.HotelierAdapter;
import com.hrs.hotelbooking.builder.CancellationDetailBuilder;
import com.hrs.hotelbooking.dao.HotelDetailsDao;
import com.hrs.hotelbooking.enumextension.HotelDetails_Enum;
import com.hrs.hotelbooking.exception.InvalidRequestException;
import com.hrs.hotelbooking.model.*;
import com.hrs.hotelbooking.repository.CancellationDetailsRepository;
import com.hrs.hotelbooking.repository.HotelDetailsRepository;
import com.hrs.hotelbooking.service.CancelBookingService;
import com.hrs.hotelbooking.utils.CacheUtil;
import com.hrs.hotelbooking.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Hashtable;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
public class CancelBookingServiceImpl implements CancelBookingService {

    @Autowired
    private HotelDetailsRepository hotelDetailsRepository;

    @Autowired
    private HotelierAdapter hotelierAdapter;

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private CancellationDetailBuilder cancellationDetailBuilder;

    @Autowired
    private CancellationDetailsRepository cancellationDetailsRepository;

    @Autowired
    private HotelDetailsDao hotelDetailsDao;

    @Override
    public Response cancelBooking(CancellationRequest cancellationRequest, ServiceContext serviceContext) {
        Response response = new Response();
        Booking booking = serviceContext.getBooking();

        // Validate booking exists
        if (booking == null || booking.getHotelDetails() == null) {
            throw new InvalidRequestException("Booking not found");
        }

        if (serviceContext.getThirdPartyResponseList() == null) {
            serviceContext.setThirdPartyResponseList(new Hashtable<>());
        }

        try {
            // Call hotelier API to cancel booking
            CompletableFuture<HotelierCancelResponse> hotelierResponse = hotelierAdapter.cancelBooking(cancellationRequest);
            HotelierCancelResponse result = hotelierResponse.get();
            if (!"SUCCESS".equals(result.getStatus())) {
                throw new RuntimeException("Failed to cancel booking at hotelier side");
            }
            serviceContext.getThirdPartyResponseList().put(Constant.HOTELIER_CANCEL_RESPONSE, result);
        } catch (Exception e) {
            throw new RuntimeException("Error cancelling booking", e);
        }

        // Create cancellation details
        List<CancellationDetails> cancellationDetails = (List<CancellationDetails>) cancellationDetailBuilder.build(serviceContext, cancellationRequest);
        cancellationDetailsRepository.saveAll(cancellationDetails);

        for (CancellationLine cancellationLine : cancellationRequest.getCancellationLines()) {
            HotelDetails hotelDetails = booking.getHotelDetails().stream().filter(detail -> detail.getLineno() == cancellationLine.getRoomLineNo()).findFirst().orElse(null);
            if (hotelDetails == null) {
                throw new InvalidRequestException("Hotel details not found");
            }
            // Update booking status            
            hotelDetails.setBookingStatus(HotelDetails_Enum.BookingStatus.CANCELLED);
            hotelDetailsDao.save(hotelDetails);
        }

        try {
            // Update cache asynchronously
            CompletableFuture.runAsync(() -> {
                CacheUtil.addHotelDetailsToCache(cancellationRequest.getBookingId(),
                        booking.getHotelDetails());
                List<BookingHistory> histories = CacheUtil.getBookingHistory(cancellationRequest.getUserId());
                if (!histories.isEmpty()) {
                    histories.stream()
                        .filter(history -> cancellationRequest.getBookingId().equals(history.getBookingId()))
                        .findFirst()
                        .ifPresent(history -> {
                            history.setBookingStatus(HotelDetails_Enum.BookingStatus.CANCELLED);
                            CacheUtil.addBookingHistory(history);
                        });
                }
            }, executorService);

            response.setStatus(true);
            response.setMessage("Booking cancelled successfully.");
        } catch (Exception e) {
            throw new RuntimeException("Error cancelling booking", e);
        }

        return response;
    }
} 