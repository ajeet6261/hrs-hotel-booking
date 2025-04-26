package com.hrs.hotelbooking.model;

import com.hrs.hotelbooking.enumextension.HotelDetails_Enum;

import java.time.LocalDateTime;

public class BookingHistory {
    private String userId;
    private String bookingId;
    private LocalDateTime createdAt;
    private HotelDetails_Enum.BookingStatus bookingStatus;

    public BookingHistory() {
    }

    public BookingHistory(String userId, String bookingId, LocalDateTime createdAt, HotelDetails_Enum.BookingStatus bookingStatus) {
        this.userId = userId;
        this.bookingId = bookingId;
        this.createdAt = createdAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public HotelDetails_Enum.BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(HotelDetails_Enum.BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}