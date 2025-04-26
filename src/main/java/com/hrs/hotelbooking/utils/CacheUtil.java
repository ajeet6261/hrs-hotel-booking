package com.hrs.hotelbooking.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrs.hotelbooking.model.BookingHistory;
import com.hrs.hotelbooking.model.HotelMetadata;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CacheUtil {
    private static final String BOOKING_HISTORY_CACHE = "booking_history";
    private static final String HOTEL_METADATA_CACHE = "hotel_metadata";
    private static CacheUtil instance;
    private final Map<String, String> cache;
    private final ObjectMapper objectMapper;

    private CacheUtil() {
        cache = new ConcurrentHashMap<>();
        objectMapper = new ObjectMapper();
    }

    public static synchronized CacheUtil getInstance() {
        if (instance == null) {
            instance = new CacheUtil();
        }
        return instance;
    }

    // Booking History Methods
    public void addBookingHistory(BookingHistory history) {
        try {
            String json = objectMapper.writeValueAsString(history);
            String key = BOOKING_HISTORY_CACHE + ":" + history.getUserId();
            cache.put(key, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing booking history", e);
        }
    }

    public BookingHistory getBookingHistory(String userId) {
        try {
            String key = BOOKING_HISTORY_CACHE + ":" + userId;
            String json = cache.get(key);
            if (json != null) {
                return objectMapper.readValue(json, BookingHistory.class);
            }
            return null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing booking history", e);
        }
    }

    public void removeBookingHistory(String userId) {
        String key = BOOKING_HISTORY_CACHE + ":" + userId;
        cache.remove(key);
    }

    public void clearBookingHistory() {
        cache.clear();
    }

    // Hotel Metadata Methods
    public void addHotelMetadata(HotelMetadata metadata) {
        try {
            String json = objectMapper.writeValueAsString(metadata);
            String key = HOTEL_METADATA_CACHE + ":" + metadata.getHotelCode();
            cache.put(key, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing hotel metadata", e);
        }
    }

    public HotelMetadata getHotelMetadata(String hotelCode) {
        try {
            String key = HOTEL_METADATA_CACHE + ":" + hotelCode;
            String json = cache.get(key);
            if (json != null) {
                return objectMapper.readValue(json, HotelMetadata.class);
            }
            return null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing hotel metadata", e);
        }
    }

    public void removeHotelMetadata(String hotelCode) {
        String key = HOTEL_METADATA_CACHE + ":" + hotelCode;
        cache.remove(key);
    }

    public void clearAll() {
        cache.clear();
    }
}
