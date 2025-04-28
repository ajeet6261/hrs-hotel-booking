package com.hrs.hotelbooking.adapter;

import com.hrs.hotelbooking.enumextension.CancelDetails_Enum;
import com.hrs.hotelbooking.model.CancellationLine;
import com.hrs.hotelbooking.model.CancellationRequest;
import com.hrs.hotelbooking.model.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CancelAdapterTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CancelAdapter cancelAdapter;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(cancelAdapter, "hotelierApiUrl", "http://localhost:8081/api");
    }

    @Test
    void cancelBooking_Success() throws Exception {
        // Arrange
        CancellationRequest request = createCancellationRequest();
        Response expectedResponse = new Response();
        expectedResponse.setStatus(true);
        expectedResponse.setMessage("Booking cancelled successfully");

        when(restTemplate.postForObject(
            eq("http://localhost:8081/api/cancel"),
            eq(request),
            eq(Response.class)
        )).thenReturn(expectedResponse);

        // Act
        CompletableFuture<Response> future = cancelAdapter.cancelBooking(request);
        Response actualResponse = future.get();

        // Assert
        assertNotNull(actualResponse);
        assertTrue(actualResponse.isStatus());
        assertEquals("Booking cancelled successfully", actualResponse.getMessage());
    }

    @Test
    void cancelBooking_WhenApiFails_ShouldReturnFallbackResponse() throws Exception {
        // Arrange
        CancellationRequest request = createCancellationRequest();
        when(restTemplate.postForObject(
            eq("http://localhost:8081/api/cancel"),
            eq(request),
            eq(Response.class)
        )).thenThrow(new RuntimeException("API Error"));

        // Act
        CompletableFuture<Response> future = cancelAdapter.cancelBooking(request);
        Response actualResponse = future.get();

        // Assert
        assertNotNull(actualResponse);
        assertFalse(actualResponse.isStatus());
        assertEquals("Service temporarily unavailable. Please try again later.", actualResponse.getMessage());
    }

    private CancellationRequest createCancellationRequest() {
        CancellationRequest request = new CancellationRequest();
        request.setBookingId("BK123456");
        request.setUserId("USER123");
        request.setReason("Change of plans");
        
        CancellationLine line = new CancellationLine();
        line.setRoomLineNo(1000);
        request.setCancellationLines(Arrays.asList(line));
        
        request.setRefundAdjustment(CancelDetails_Enum.Refund_Adjustment.Refund_To_Original_Card);
        return request;
    }
} 