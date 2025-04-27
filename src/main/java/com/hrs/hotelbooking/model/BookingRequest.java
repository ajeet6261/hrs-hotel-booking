package com.hrs.hotelbooking.model;

import java.util.List;

public class BookingRequest extends Request {
    private String hotelCode;
    private List<RoomRequest> roomRequest;
    private String checkInDate;
    private String checkOutDate;
    private boolean freeCancellation;
    private String currencyCode;
    private double currencyFactor;
    private String remarks;
    private String countryOfTravel;
    private String city;

    public String getHotelCode() {
        return hotelCode;
    }

    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
    }

    public List<RoomRequest> getRoomRequest() {
        return roomRequest;
    }

    public void setRoomRequest(List<RoomRequest> roomRequest) {
        this.roomRequest = roomRequest;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public boolean isFreeCancellation() {
        return freeCancellation;
    }

    public void setFreeCancellation(boolean freeCancellation) {
        this.freeCancellation = freeCancellation;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public double getCurrencyFactor() {
        return currencyFactor;
    }

    public void setCurrencyFactor(double currencyFactor) {
        this.currencyFactor = currencyFactor;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCountryOfTravel() {
        return countryOfTravel;
    }

    public void setCountryOfTravel(String countryOfTravel) {
        this.countryOfTravel = countryOfTravel;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
