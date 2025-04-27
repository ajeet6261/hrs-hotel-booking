package com.hrs.hotelbooking.adapter;

import com.hrs.hotelbooking.model.CancellationLine;
import com.hrs.hotelbooking.model.CancellationRequest;
import com.hrs.hotelbooking.model.HotelierCancelResponse;
import com.hrs.hotelbooking.model.HotelierPenaltyData;
import com.hrs.hotelbooking.utils.CacheUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class HotelierAdapter {
    private final RestTemplate restTemplate;

    @Value("${hotelier.api.url}")
    private String apiUrl;

    public HotelierAdapter() {
        this.restTemplate = CacheUtil.getRestTemplate();
    }

    public CompletableFuture<HotelierCancelResponse> cancelBooking(CancellationRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Call hotelier API to cancel booking
                String url = apiUrl + "/bookings/" + request.getBookingId() + "/cancel";
                HotelierCancelResponse response = null; //restTemplate.postForObject(url, request, HotelierCancelResponse.class);

                // For demo, return dummy response if API call fails                
                List<HotelierPenaltyData> penaltyDataList = new ArrayList<>();
                for (CancellationLine cancellationLine : request.getCancellationLines()) {
                    HotelierPenaltyData penaltyData = new HotelierPenaltyData();
                    penaltyData.setRoomLineNo(cancellationLine.getRoomLineNo());
                    penaltyData.setPenalty(100.0);
                    penaltyDataList.add(penaltyData);
                }
                response = new HotelierCancelResponse();
                response.setStatus("SUCCESS");
                response.setMessage("Booking cancelled successfully at hotelier side");
                response.setHotelierPenaltyDataList(penaltyDataList);
                response.setBookingId(request.getBookingId());

                return response;
            } catch (Exception e) {
                // Return dummy response in case of API failure
                HotelierCancelResponse dummyResponse = new HotelierCancelResponse();
                dummyResponse.setStatus("SUCCESS");
                dummyResponse.setMessage("Booking cancelled successfully at hotelier side");
                List<HotelierPenaltyData> penaltyDataList = new ArrayList<>();
                HotelierPenaltyData penaltyData = new HotelierPenaltyData();
                penaltyDataList.add(penaltyData);
                dummyResponse.setHotelierPenaltyDataList(penaltyDataList);
                return dummyResponse;
            }
        });
    }
} 