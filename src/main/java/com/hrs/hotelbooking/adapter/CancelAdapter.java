package com.hrs.hotelbooking.adapter;

import com.hrs.hotelbooking.model.CancellationRequest;
import com.hrs.hotelbooking.model.Response;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Component
public class CancelAdapter {

    private static final Logger logger = LoggerFactory.getLogger(CancelAdapter.class);

    @Value("${user.cancel.url}")
    private String cancelApiUrl;

    @Autowired
    private RestTemplate restTemplate;

    @CircuitBreaker(name = "cancelAdapter", fallbackMethod = "cancelBookingFallback")
    @Retry(name = "cancelAdapter")
    public CompletableFuture<Response> cancelBooking(CancellationRequest request) {
        logger.info("Calling cancellation API for bookingId: {}", request.getBookingId());
        return CompletableFuture.supplyAsync(() -> {
            try {
                return restTemplate.postForObject(
                    cancelApiUrl,
                    request,
                    Response.class
                );
            } catch (Exception e) {
                logger.error("Error calling cancellation API for bookingId: {}", request.getBookingId(), e);
                throw new RuntimeException("Failed to cancel booking", e);
            }
        });
    }
    @SuppressWarnings("unused")
    private CompletableFuture<Response> cancelBookingFallback(CancellationRequest request, Exception e) {
        logger.warn("Circuit breaker triggered for bookingId: {}. Using fallback response.", request.getBookingId());
        Response fallbackResponse = new Response();
        fallbackResponse.setStatus(false);
        fallbackResponse.setMessage("Service temporarily unavailable. Please try again later.");
        return CompletableFuture.completedFuture(fallbackResponse);
    }
} 