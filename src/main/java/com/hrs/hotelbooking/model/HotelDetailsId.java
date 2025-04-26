package com.hrs.hotelbooking.model;

import java.io.Serializable;

public class HotelDetailsId implements Serializable {
    private String bookingId;
    private int lineno;

    public HotelDetailsId() {
    }

    public HotelDetailsId(String bookingId, int lineno) {
        this.bookingId = bookingId;
        this.lineno = lineno;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public int getLineno() {
        return lineno;
    }

    public void setLineno(int lineno) {
        this.lineno = lineno;
    }
}