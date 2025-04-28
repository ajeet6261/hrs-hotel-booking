package com.hrs.hotelbooking.model;

import com.hrs.hotelbooking.enumextension.HotelDetails_Enum;

import java.time.LocalDateTime;
import java.util.Date;

public class BookingHistory {
    private String userId;
    private String bookingId;
    private Date createdAt;
    private HotelDetails_Enum.BookingStatus bookingStatus;

    public BookingHistory() {
    }

    public BookingHistory(String userId, String bookingId, LocalDateTime createdAt, HotelDetails_Enum.BookingStatus bookingStatus) {
        this.userId = userId;
        this.bookingId = bookingId;
        this.bookingStatus = bookingStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public HotelDetails_Enum.BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(HotelDetails_Enum.BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}