package com.hrs.hotelbooking.model;

import java.util.List;

public class Booking {
    private String bookingId;
    private User user;
    private List<HotelDetails> hotelDetails;
    private Hotel hotel;
    private List<CancellationDetails> cancellationDetails;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<HotelDetails> getHotelDetails() {
        return hotelDetails;
    }

    public void setHotelDetails(List<HotelDetails> hotelDetails) {
        this.hotelDetails = hotelDetails;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public List<CancellationDetails> getCancellationDetails() {
        return cancellationDetails;
    }

    public void setCancellationDetails(List<CancellationDetails> cancellationDetails) {
        this.cancellationDetails = cancellationDetails;
    }
}
