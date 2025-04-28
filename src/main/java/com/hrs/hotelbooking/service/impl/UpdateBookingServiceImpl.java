package com.hrs.hotelbooking.service.impl;

import com.hrs.hotelbooking.adapter.CancelAdapter;
import com.hrs.hotelbooking.adapter.HotelierAdapter;
import com.hrs.hotelbooking.enumextension.CancelDetails_Enum;
import com.hrs.hotelbooking.enumextension.HotelDetails_Enum;
import com.hrs.hotelbooking.exception.InvalidRequestException;
import com.hrs.hotelbooking.model.*;
import com.hrs.hotelbooking.service.UpdateBookingService;
import com.hrs.hotelbooking.utils.CacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
public class UpdateBookingServiceImpl implements UpdateBookingService {

    private static final Logger logger = LoggerFactory.getLogger(UpdateBookingServiceImpl.class);

    @Autowired
    private CancelAdapter cancelAdapter;

    @Autowired
    private HotelierAdapter hotelAdapter;

    @Autowired
    ExecutorService executorService;

    private void validateBooking(String bookingId, String userId, ServiceContext serviceContext) {
        logger.debug("Validating booking for bookingId: {} and userId: {}", bookingId, userId);

        if (bookingId == null || bookingId.trim().isEmpty()) {
            logger.error("Booking ID is required");
            throw new InvalidRequestException("Booking ID is required");
        }

        if (userId == null || userId.trim().isEmpty()) {
            logger.error("User ID is required");
            throw new InvalidRequestException("User ID is required");
        }

        // Validate booking exists
        if (!serviceContext.getBooking().getBookingId().equals(bookingId)) {
            logger.error("Booking not found for bookingId: {}", bookingId);
            throw new InvalidRequestException("Booking not found");
        }

        // Validate user exists and is active
        if (!serviceContext.getBooking().getUser().getUserId().equals(userId)) {
            logger.error("User not found or inactive for userId: {}", userId);
            throw new InvalidRequestException("User not found or inactive");
        }

        Optional<CancellationDetails> details = serviceContext.getBooking().getCancellationDetails().stream().filter(cancellationDetails -> cancellationDetails.getRequestType() == CancelDetails_Enum.RequestType.Cancellation.getCode()).findFirst();
        if (details.isPresent()) {
            throw new InvalidRequestException("Booking update not allowed for cancelled booking ");
        }

        logger.debug("Validation successful for bookingId: {} and userId: {}", bookingId, userId);
    }

    @Override
    public CompletableFuture<Response> updateBooking(BookingRequest bookingRequest, ServiceContext serviceContext) {
        logger.info("Processing update request for bookingId: {}", bookingRequest.getBookingId());
        Response response = new Response(true, bookingRequest.getUserId(), bookingRequest.getBookingId(), "Date change request processed successfully");
        return CompletableFuture.supplyAsync(
                () -> {
                    // Validate booking and user
                    validateBooking(bookingRequest.getBookingId(), bookingRequest.getUserId(), serviceContext);

                    // confirm bookking at hotel end.
                    HotelBookResponse hotelBookResponse = hotelAdapter.bookHotel(bookingRequest).join();
                    if (hotelBookResponse.getStatus().equals("SUCCESS")) {
                        logger.debug("Booking confirmed at hotel end for bookingId: {}", bookingRequest.getBookingId());
                    } else {
                        logger.error("Booking failed at hotel end for bookingId: {}", bookingRequest.getBookingId());
                        throw new InvalidRequestException("Booking failed at hotel end");
                    }
                    // call cancel booking to mark Date change request
                    CancellationRequest cancellationRequest = new CancellationRequest();
                    cancellationRequest.setBookingId(bookingRequest.getBookingId());
                    cancellationRequest.setUserId(bookingRequest.getUserId());
                    double newSellingPrice = bookingRequest.getRoomRequest().stream().mapToDouble(RoomRequest::getSellingPrice).sum();
                    double existingSellingPrice = serviceContext.getBooking().getHotelDetails().stream().mapToDouble(HotelDetails::getSellingPrice).sum();
                    double refundAmount = 0;
                    if (existingSellingPrice > newSellingPrice) {
                        refundAmount = existingSellingPrice - newSellingPrice;
                    }
                    List<CancellationLine> cancellationLines = getCancellationLines(bookingRequest, serviceContext, refundAmount);
                    cancellationRequest.setCancellationLines(cancellationLines);
                    cancellationRequest.setRequestType(CancelDetails_Enum.RequestType.DateChange.getCode());
                    cancellationRequest.setRefundAdjustment(CancelDetails_Enum.Refund_Adjustment.Refund_To_Original_Card);
                    cancellationRequest.setReason("modification");
                    cancellationRequest.setRequestType(CancelDetails_Enum.RequestType.DateChange.getCode());
                    cancellationRequest.setBookingId(bookingRequest.getBookingId());
                    Response cancelResponse = cancelAdapter.cancelBooking(cancellationRequest).join();

                    if (cancelResponse.isStatus()) {
                        for (HotelDetails hotelDetails : serviceContext.getBooking().getHotelDetails()) {
                            hotelDetails.setHotelStatus(HotelDetails_Enum.HotelStatus.RECONFIRMED);
                            hotelDetails.setBaseAmount(hotelDetails.getBaseAmount());
                            hotelDetails.setSellingPrice(hotelDetails.getSellingPrice());
                        }
                    } else {
                        response.setStatus(false);
                        response.setMessage(cancelResponse.getMessage());
                        return response;
                    }
                    try {
                        // Update cache asynchronously
                        logger.debug("Initiating async cache updates for bookingId: {}", cancellationRequest.getBookingId());
                        CompletableFuture.runAsync(() -> {
                            logger.debug("Updating cache with hotel details for bookingId: {}", cancellationRequest.getBookingId());
                            CacheUtil.addHotelDetailsToCache(cancellationRequest.getBookingId(),
                                    serviceContext.getBooking().getHotelDetails());
                            List<BookingHistory> histories = CacheUtil.getBookingHistory(cancellationRequest.getUserId());
                            if (!histories.isEmpty()) {
                                histories.stream()
                                        .filter(history -> cancellationRequest.getBookingId().equals(history.getBookingId()))
                                        .findFirst()
                                        .ifPresent(history -> {
                                            history.setBookingStatus(HotelDetails_Enum.BookingStatus.CONFIRMED);
                                            CacheUtil.addBookingHistory(history);
                                            logger.debug("Updated booking history status to CONFIRMED for bookingId: {}", history.getBookingId());
                                        });
                            } else {
                                BookingHistory bookingHistory = new BookingHistory();
                                bookingHistory.setUserId(cancellationRequest.getUserId());
                                bookingHistory.setBookingId(cancellationRequest.getBookingId());
                                bookingHistory.setCreatedAt(new Date());
                                bookingHistory.setBookingStatus(HotelDetails_Enum.BookingStatus.CONFIRMED);
                                CacheUtil.addBookingHistory(bookingHistory);
                            }
                        }, executorService);
                    } catch (InvalidRequestException e) {
                        throw e;
                    } catch (Exception e) {
                        logger.error("Error updating cache for bookingId: {}", cancellationRequest.getBookingId(), e);
                        throw new RuntimeException("Error in updating booking", e);
                    }
                    return response;
                }, executorService
        );
    }

    private static List<CancellationLine> getCancellationLines(BookingRequest bookingRequest, ServiceContext serviceContext, double refundAmount) {
        List<CancellationLine> cancellationLines = new ArrayList<>();
        for (HotelDetails hotelDetails : serviceContext.getBooking().getHotelDetails()) {
            CancellationLine cancellationLine = new CancellationLine();
            cancellationLine.setRoomLineNo(hotelDetails.getLineno());
            cancellationLine.setRefundAmount(refundAmount / bookingRequest.getRoomRequest().size());
            cancellationLines.add(cancellationLine);
        }
        return cancellationLines;
    }
}
