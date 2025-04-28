package com.hrs.hotelbooking.adapter;

import com.hrs.hotelbooking.model.CancellationLine;
import com.hrs.hotelbooking.model.CancellationRequest;
import com.hrs.hotelbooking.model.HotelierCancelResponse;
import com.hrs.hotelbooking.model.HotelierPenaltyData;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class HotelierAdapter {

    private static final Logger logger = LoggerFactory.getLogger(HotelierAdapter.class);

    @Value("${hotelier.api.url}")
    private String hotelierApiUrl;

    @Autowired
    private RestTemplate restTemplate;

    @CircuitBreaker(name = "hotelierAdapter", fallbackMethod = "cancelBookingFallback")
    @Retry(name = "hotelierAdapter")
    public CompletableFuture<HotelierCancelResponse> cancelBooking(CancellationRequest request) {
        logger.info("Calling hotelier API to cancel booking for bookingId: {}", request.getBookingId());
        return CompletableFuture.supplyAsync(() -> {
            try {
                return restTemplate.postForObject(
                        hotelierApiUrl + "/cancel",
                        request,
                        HotelierCancelResponse.class
                );
            } catch (Exception e) {
                logger.error("Error calling hotelier API for bookingId: {}", request.getBookingId(), e);
                throw new RuntimeException("Failed to cancel booking at hotelier side", e);
            }
        });
    }

    private CompletableFuture<HotelierCancelResponse> cancelBookingFallback(CancellationRequest request, Exception e) {
        logger.warn("Circuit breaker triggered for bookingId: {}. Using fallback response.", request.getBookingId());
        List<HotelierPenaltyData> penaltyDataList = new ArrayList<>();
        for (CancellationLine cancellationLine : request.getCancellationLines()) {
            HotelierPenaltyData penaltyData = new HotelierPenaltyData();
            penaltyData.setRoomLineNo(cancellationLine.getRoomLineNo());
            penaltyData.setPenalty(100.0);
            penaltyDataList.add(penaltyData);
        }
        HotelierCancelResponse response = new HotelierCancelResponse();
        response = new HotelierCancelResponse();
        response.setStatus("SUCCESS");
        response.setMessage("Booking cancelled successfully at hotelier side");
        response.setHotelierPenaltyDataList(penaltyDataList);
        response.setBookingId(request.getBookingId());
        return CompletableFuture.completedFuture(response);
    }
} 