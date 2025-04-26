package com.hrs.hotelbooking.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDetailsId implements Serializable {
    private String bookingId;
    private int lineno;
} 