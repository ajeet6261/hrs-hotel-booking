package com.hrs.hotelbooking.dao;

import com.hrs.hotelbooking.enumextension.HotelDetails_Enum;
import com.hrs.hotelbooking.model.BookingRequest;
import com.hrs.hotelbooking.model.Hotel;
import com.hrs.hotelbooking.model.HotelDetails;
import com.hrs.hotelbooking.model.RoomRequest;
import com.hrs.hotelbooking.repository.HotelDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
public class HotelDetailsDao {

    private static final String ALPHA_NUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BOOKING_ID_LENGTH = 10;
    private static final String BOOKING_PREFIX = "HRS";
    private static final int RANDOM_LENGTH = BOOKING_ID_LENGTH - BOOKING_PREFIX.length();
    private static final int INITIAL_LINE_NO = 1000;
    private static final int LINE_NO_INCREMENT = 1000;
    private final Random random = new Random();

    @Autowired
    private HotelDetailsRepository hotelDetailsRepository;

    public List<HotelDetails> prepareHotelDetails(Hotel hotel, BookingRequest bookingRequest) {
        List<HotelDetails> hotelDetailsList = new ArrayList<>();
        List<RoomRequest> roomRequests = bookingRequest.getRoomRequest();
        String bookingId = generateBookingId();
        int currentLineNo = INITIAL_LINE_NO;

        for (RoomRequest roomRequest : roomRequests) {
            HotelDetails hotelDetails = new HotelDetails();

            // Set booking details
            hotelDetails.setBookingId(bookingId);
            hotelDetails.setLineno(currentLineNo);
            hotelDetails.setCountryOfTravel(bookingRequest.getCountryOfTravel());
            hotelDetails.setTravelCity(bookingRequest.getCity());

            // Convert string dates to Date objects
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                hotelDetails.setCheckInDate(dateFormat.parse(bookingRequest.getCheckInDate()));
                hotelDetails.setCheckOutDate(dateFormat.parse(bookingRequest.getCheckOutDate()));
            } catch (ParseException e) {
                throw new RuntimeException("Error parsing dates", e);
            }

            // Set hotel details
            hotelDetails.setHotelCode(hotel.getCode());
            hotelDetails.setHotelName(hotel.getName());
            hotelDetails.setBookingStatus(HotelDetails_Enum.BookingStatus.CONFIRMED);
            hotelDetails.setHotelStatus(HotelDetails_Enum.HotelStatus.CONFIRMED);

            // Calculate number of nights
            long nights = calculateNights(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate());
            hotelDetails.setNoOfNights((int) nights);

            // Set room details
            hotelDetails.setRoomType(convertToRoomType(roomRequest.getRoomType()));
            hotelDetails.setVendorNo(hotel.getVendorno());
            hotelDetails.setNoOfRooms(1);

            // Set pricing details
            hotelDetails.setBaseAmount(roomRequest.getBaseAmount());
            hotelDetails.setIgstAmount(roomRequest.getIgstAmount());
            hotelDetails.setCgstAmount(roomRequest.getCgstAmount());
            hotelDetails.setSgstAmount(roomRequest.getSgstAmount());
            hotelDetails.setTaxAmount(roomRequest.getTaxAmount());
            hotelDetails.setSellingPrice(roomRequest.getSellingPrice());
            hotelDetails.setDiscountAmount(roomRequest.getDiscountAmount());

            // Set currency details
            hotelDetails.setCurrencyCode(bookingRequest.getCurrencyCode());
            hotelDetails.setCurrencyFactor(bookingRequest.getCurrencyFactor());

            // Set booking creation date
            hotelDetails.setBookingCreatedDate(new Date());

            // Set remarks if any
            hotelDetails.setRemarks(bookingRequest.getRemarks());

            hotelDetailsList.add(hotelDetails);
            currentLineNo += LINE_NO_INCREMENT;
        }
        return hotelDetailsList;
    }

    public HotelDetails save(HotelDetails hotelDetails) {
        return hotelDetailsRepository.save(hotelDetails);
    }

    public List<HotelDetails> saveAll(List<HotelDetails> hotelDetailsList) {
        return hotelDetailsRepository.saveAll(hotelDetailsList);
    }

    private String generateBookingId() {
        StringBuilder bookingId = new StringBuilder(BOOKING_ID_LENGTH);
        bookingId.append(BOOKING_PREFIX);

        for (int i = 0; i < RANDOM_LENGTH; i++) {
            int index = random.nextInt(ALPHA_NUMERIC.length());
            bookingId.append(ALPHA_NUMERIC.charAt(index));
        }
        return bookingId.toString();
    }

    private long calculateNights(String checkInDate, String checkOutDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date checkIn = dateFormat.parse(checkInDate);
            Date checkOut = dateFormat.parse(checkOutDate);
            return (checkOut.getTime() - checkIn.getTime()) / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            throw new RuntimeException("Error calculating nights", e);
        }
    }

    private HotelDetails_Enum.RoomType convertToRoomType(String roomType) {
        switch (roomType.toLowerCase()) {
            case "single":
                return HotelDetails_Enum.RoomType.Single;
            case "double":
                return HotelDetails_Enum.RoomType.Double;
            case "double_1_extra_bed":
                return HotelDetails_Enum.RoomType.Double_1_Extra_Bed;
            default:
                throw new IllegalArgumentException("Invalid room type: " + roomType);
        }
    }
} 