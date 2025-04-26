package com.hrs.hotelbooking.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Hotel metadata information")
public class HotelMetadata {
    @Schema(description = "Unique identifier for the hotel", example = "TAJ001")
    private String hotelCode;

    @Schema(description = "List of hotel image URLs", example = "[\"https://example.com/image1.jpg\"]")
    private List<String> imageUrls;

    @Schema(description = "Hotel description", example = "Luxury 5-star hotel in Mumbai")
    private String description;

    @Schema(description = "List of available amenities", example = "[\"Swimming Pool\", \"Spa\", \"Fine Dining\"]")
    private List<String> amenities;

    @Schema(description = "Hotel rating (0-5)", example = "4.8")
    private double rating;

    @Schema(description = "Total number of reviews", example = "1200")
    private int totalReviews;

    @Schema(description = "Hotel location", example = "Colaba, Mumbai")
    private String location;

    @Schema(description = "Check-in time", example = "14:00")
    private String checkInTime;

    @Schema(description = "Check-out time", example = "12:00")
    private String checkOutTime;

    @Schema(description = "Base price per night", example = "10000.0")
    private double basePrice;

    public HotelMetadata() {
    }

    public HotelMetadata(String hotelCode, List<String> imageUrls, String description,
                         List<String> amenities, double rating, int totalReviews,
                         String location, String checkInTime, String checkOutTime, double basePrice) {
        this.hotelCode = hotelCode;
        this.imageUrls = imageUrls;
        this.description = description;
        this.amenities = amenities;
        this.rating = rating;
        this.totalReviews = totalReviews;
        this.location = location;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.basePrice = basePrice;
    }

    public String getHotelCode() {
        return hotelCode;
    }

    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
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

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }
}