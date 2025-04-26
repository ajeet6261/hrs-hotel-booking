package com.hrs.hotelbooking.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    private String bookingId;
    private User user;
    private List<HotelDetails> hotelDetails;
    private Hotel hotel;
    private List<CancellationDetails> cancellationDetails;
}
