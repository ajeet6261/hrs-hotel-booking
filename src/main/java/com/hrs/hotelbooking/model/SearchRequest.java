package com.hrs.hotelbooking.model;

import java.time.LocalDate;

public class SearchRequest {
    private String city;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public SearchRequest() {
        this.city = "";
        this.checkInDate = LocalDate.now();
        this.checkOutDate = LocalDate.now().plusDays(1);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
}