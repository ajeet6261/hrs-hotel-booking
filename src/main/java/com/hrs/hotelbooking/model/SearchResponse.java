package com.hrs.hotelbooking.model;

import java.util.List;

public class SearchResponse {
    private String hotelCode;
    private String name;
    private String city;
    private String category;
    private double basePrice;
    private List<String> imageUrls;
    private String description;
    private List<String> amenities;
    private double rating;
    private int totalReviews;
    private String location;
    private String checkInTime;
    private String checkOutTime;
    private boolean available;

    public SearchResponse() {
    }

    public SearchResponse(String hotelCode, String name, String city, String category, double basePrice, List<String> imageUrls, String description, List<String> amenities, double rating, int totalReviews, String location, String checkInTime, String checkOutTime, boolean available) {
        this.hotelCode = hotelCode;
        this.name = name;
        this.city = city;
        this.category = category;
        this.basePrice = basePrice;
        this.imageUrls = imageUrls;
        this.description = description;
        this.amenities = amenities;
        this.rating = rating;
        this.totalReviews = totalReviews;
        this.location = location;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.available = available;
    }

    public String getHotelCode() {
        return hotelCode;
    }

    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<String> amenities) {
        this.amenities = amenities;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(int totalReviews) {
        this.totalReviews = totalReviews;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
