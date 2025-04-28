package com.hrs.hotelbooking.model;

public class Response {
    private boolean status;
    private String userId;
    private String message;
    private String bookingId;

    public Response() {
    }

    public Response(boolean status, String userId, String message, String bookingId) {
        this.status = status;
        this.userId = userId;
        this.message = message;
        this.bookingId = bookingId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }
}
