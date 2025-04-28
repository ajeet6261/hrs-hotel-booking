package com.hrs.hotelbooking.service.impl;

import com.hrs.hotelbooking.adapter.HotelierAdapter;
import com.hrs.hotelbooking.builder.CancellationDetailBuilder;
import com.hrs.hotelbooking.dao.HotelDetailsDao;
import com.hrs.hotelbooking.enumextension.CancelDetails_Enum;
import com.hrs.hotelbooking.enumextension.HotelDetails_Enum;
import com.hrs.hotelbooking.exception.InvalidRequestException;
import com.hrs.hotelbooking.model.*;
import com.hrs.hotelbooking.repository.CancellationDetailsRepository;
import com.hrs.hotelbooking.repository.HotelDetailsRepository;
import com.hrs.hotelbooking.service.CancelBookingService;
import com.hrs.hotelbooking.utils.CacheUtil;
import com.hrs.hotelbooking.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
public class CancelBookingServiceImpl implements CancelBookingService {

    private static final Logger logger = LoggerFactory.getLogger(CancelBookingServiceImpl.class);
    

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
    public CompletableFuture<Response> cancelBooking(CancellationRequest cancellationRequest, ServiceContext serviceContext) {
        logger.info("Processing cancellation request for bookingId: {}", cancellationRequest.getBookingId());

        return CompletableFuture.supplyAsync(() -> {
            Response response = new Response();
            Booking booking = serviceContext.getBooking();

            // Validate booking exists
            if (booking == null || booking.getHotelDetails() == null) {
                logger.error("Booking not found for bookingId: {}", cancellationRequest.getBookingId());
                throw new InvalidRequestException("Booking not found");
            }

            if (serviceContext.getThirdPartyResponseList() == null) {
                serviceContext.setThirdPartyResponseList(new Hashtable<>());
            }

            Optional<CancellationDetails> details = serviceContext.getBooking().getCancellationDetails().stream().filter(cancellationDetails -> cancellationDetails.getBookingId().equalsIgnoreCase(cancellationRequest.getBookingId())
                    && cancellationDetails.getRequestType() == CancelDetails_Enum.RequestType.Cancellation.getCode()).findFirst();
            if (details.isPresent()) {
                throw new InvalidRequestException("Booking is already cancelled");
            }

            try {
                // Call hotelier API to cancel booking
                logger.debug("Calling hotelier API to cancel booking for bookingId: {}", cancellationRequest.getBookingId());
                HotelierCancelResponse result = hotelierAdapter.cancelBooking(cancellationRequest).get();
                if (!"SUCCESS".equals(result.getStatus())) {
                    logger.error("Failed to cancel booking at hotelier side for bookingId: {}", cancellationRequest.getBookingId());
                    throw new RuntimeException("Failed to cancel booking at hotelier side");
                }
                serviceContext.getThirdPartyResponseList().put(Constant.HOTELIER_CANCEL_RESPONSE, result);
                logger.debug("Successfully cancelled booking at hotelier side for bookingId: {}", cancellationRequest.getBookingId());
            } catch (Exception e) {
                logger.error("Error cancelling booking at hotelier side for bookingId: {}", cancellationRequest.getBookingId(), e);
                throw new RuntimeException("Error cancelling booking", e);
            }

            // Create cancellation details
            logger.debug("Creating cancellation details for bookingId: {}", cancellationRequest.getBookingId());
            List<CancellationDetails> cancellationDetails = (List<CancellationDetails>) cancellationDetailBuilder.build(serviceContext, cancellationRequest);
            cancellationDetailsRepository.saveAll(cancellationDetails);

            for (CancellationLine cancellationLine : cancellationRequest.getCancellationLines()) {
                HotelDetails hotelDetails = booking.getHotelDetails().stream()
                        .filter(detail -> detail.getLineno() == cancellationLine.getRoomLineNo())
                        .findFirst()
                        .orElse(null);
                if (hotelDetails == null) {
                    logger.error("Hotel details not found for lineNo: {}", cancellationLine.getRoomLineNo());
                    throw new InvalidRequestException("Hotel details not found");
                }
                // Update booking status            
                hotelDetails.setBookingStatus(HotelDetails_Enum.BookingStatus.CANCELLED);
                hotelDetailsDao.save(hotelDetails);
                logger.debug("Updated booking status to CANCELLED for lineNo: {}", cancellationLine.getRoomLineNo());
            }

            try {
                // Update cache asynchronously
                logger.debug("Initiating async cache updates for bookingId: {}", cancellationRequest.getBookingId());
                CompletableFuture.runAsync(() -> {
                    logger.debug("Updating cache with hotel details for bookingId: {}", cancellationRequest.getBookingId());
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
                                    logger.debug("Updated booking history status to CANCELLED for bookingId: {}", history.getBookingId());
                                });
                    } else {
                        BookingHistory bookingHistory = new BookingHistory();
                        bookingHistory.setUserId(cancellationRequest.getUserId());
                        bookingHistory.setBookingId(cancellationRequest.getBookingId());
                        bookingHistory.setCreatedAt(new Date());
                        bookingHistory.setBookingStatus(HotelDetails_Enum.BookingStatus.CANCELLED);
                        CacheUtil.addBookingHistory(bookingHistory);
                    }
                }, executorService);

                response.setStatus(true);
                response.setUserId(cancellationRequest.getUserId());
                response.setMessage("Booking cancelled successfully.");
                logger.info("Successfully cancelled booking for bookingId: {}", cancellationRequest.getBookingId());
            } catch (Exception e) {
                logger.error("Error updating cache for bookingId: {}", cancellationRequest.getBookingId(), e);
                throw new RuntimeException("Error cancelling booking", e);
            }

            return response;
        }, executorService);
    }
} 