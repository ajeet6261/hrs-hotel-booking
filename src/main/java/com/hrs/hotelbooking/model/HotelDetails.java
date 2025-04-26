package com.hrs.hotelbooking.model;

import com.hrs.hotelbooking.enumextension.HotelDetails_Enum;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import java.util.Date;

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
    private Date bookingCreatedDate;
    private String remarks;

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

    public String getCountryOfTravel() {
        return countryOfTravel;
    }

    public void setCountryOfTravel(String countryOfTravel) {
        this.countryOfTravel = countryOfTravel;
    }

    public String getTravelCity() {
        return travelCity;
    }

    public void setTravelCity(String travelCity) {
        this.travelCity = travelCity;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getHotelCode() {
        return hotelCode;
    }

    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public HotelDetails_Enum.BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(HotelDetails_Enum.BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public HotelDetails_Enum.HotelStatus getHotelStatus() {
        return hotelStatus;
    }

    public void setHotelStatus(HotelDetails_Enum.HotelStatus hotelStatus) {
        this.hotelStatus = hotelStatus;
    }

    public int getNoOfNights() {
        return noOfNights;
    }

    public void setNoOfNights(int noOfNights) {
        this.noOfNights = noOfNights;
    }

    public HotelDetails_Enum.RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(HotelDetails_Enum.RoomType roomType) {
        this.roomType = roomType;
    }

    public String getVendorNo() {
        return vendorNo;
    }

    public void setVendorNo(String vendorNo) {
        this.vendorNo = vendorNo;
    }

    public int getNoOfRooms() {
        return noOfRooms;
    }

    public void setNoOfRooms(int noOfRooms) {
        this.noOfRooms = noOfRooms;
    }

    public double getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(double baseAmount) {
        this.baseAmount = baseAmount;
    }

    public double getIgstAmount() {
        return igstAmount;
    }

    public void setIgstAmount(double igstAmount) {
        this.igstAmount = igstAmount;
    }

    public double getCgstAmount() {
        return cgstAmount;
    }

    public void setCgstAmount(double cgstAmount) {
        this.cgstAmount = cgstAmount;
    }

    public double getSgstAmount() {
        return sgstAmount;
    }

    public void setSgstAmount(double sgstAmount) {
        this.sgstAmount = sgstAmount;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
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

    public Date getBookingCreatedDate() {
        return bookingCreatedDate;
    }

    public void setBookingCreatedDate(Date bookingCreatedDate) {
        this.bookingCreatedDate = bookingCreatedDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}