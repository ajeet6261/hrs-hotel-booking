package com.hrs.hotelbooking.model;

import java.util.List;

public class HotelierCancelResponse {
    private String bookingId;
    private String status;    
    private List<HotelierPenaltyData> hotelierPenaltyDataList;
    private String message;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<HotelierPenaltyData> getHotelierPenaltyDataList() {
        return hotelierPenaltyDataList;
    }

    public void setHotelierPenaltyDataList(List<HotelierPenaltyData> hotelierPenaltyDataList) {
        this.hotelierPenaltyDataList = hotelierPenaltyDataList;
    }
}
