package com.hrs.hotelbooking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import java.util.Date;

import com.hrs.hotelbooking.enumextension.HotelDetails_Enum;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotel_details")
@IdClass(HotelDetailsId.class)
public class HotelDetails {
    @Id
    private String bookingId;
    @Id
    private int lineno;
    private String countryOfTravel;
    private String travelCity;
    private Date checkInDate;
    private Date checkOutDate;
    private String hotelCode;
    private String hotelName;
    private HotelDetails_Enum.BookingStatus bookingStatus;
    private HotelDetails_Enum.HotelStatus hotelStatus;
    private int noOfNights;
    private HotelDetails_Enum.RoomType roomType;
    private String vendorNo;
    private int noOfRooms;
    private double baseAmount;
    private double igstAmount;
    private double cgstAmount;
    private double sgstAmount;    
    private double taxAmount;
    private double sellingPrice;
    private double discountAmount;    
    private String currencyCode;
    private double currencyFactor;
    private Data bookingCreatedDate;
    private String remarks;
}