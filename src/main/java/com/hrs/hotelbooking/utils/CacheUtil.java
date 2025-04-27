package com.hrs.hotelbooking.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hrs.hotelbooking.enumextension.HotelDetails_Enum;
import com.hrs.hotelbooking.model.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CacheUtil {
    private static final String BOOKING_HISTORY_CACHE = "booking_history";
    private static final String HOTEL_METADATA_CACHE = "hotel_metadata";
    private static final String HOTEL_DETAILS_CACHE = "hotel_details";
    private static final String CANCELLATION_POLICY_CACHE = "cancellation_policy";
    private static CacheUtil instance;
    private final Map<String, String> cache;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    private CacheUtil() {
        cache = new ConcurrentHashMap<>();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        restTemplate = new RestTemplate();
    }

    public static synchronized CacheUtil getInstance() {
        if (instance == null) {
            instance = new CacheUtil();
        }
        return instance;
    }

    public static void addHotelDetailsToCache(String bookingId, List<HotelDetails> hotelDetails) {
        try {
            String json = getInstance().objectMapper.writeValueAsString(hotelDetails);
            String key = HOTEL_DETAILS_CACHE + ":" + bookingId;
            getInstance().cache.put(key, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing hotel details", e);
        }
    }

    public static List<HotelDetails> getHotelDetailsFromCache(String bookingId) {
        try {
            String key = HOTEL_DETAILS_CACHE + ":" + bookingId;
            String json = getInstance().cache.get(key);
            if (json != null) {
                return getInstance().objectMapper.readValue(json,
                        getInstance().objectMapper.getTypeFactory().constructCollectionType(List.class, HotelDetails.class));
            }
            return null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing hotel details", e);
        }
    }

    // Booking History Methods
    public static void addBookingHistory(BookingHistory history) {
        try {
            String userId = history.getUserId();
            String key = BOOKING_HISTORY_CACHE + ":" + userId;
            String existingJson = getInstance().cache.get(key);
            List<BookingHistory> histories;

            if (existingJson != null) {
                histories = getInstance().objectMapper.readValue(existingJson,
                        getInstance().objectMapper.getTypeFactory().constructCollectionType(List.class, BookingHistory.class));
            } else {
                histories = new ArrayList<>();
            }

            histories.add(history);
            String updatedJson = getInstance().objectMapper.writeValueAsString(histories);
            getInstance().cache.put(key, updatedJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error handling booking history", e);
        }
    }

    public static List<BookingHistory> getBookingHistory(String userId) {
        try {
            String key = BOOKING_HISTORY_CACHE + ":" + userId;
            String json = getInstance().cache.get(key);
            if (json != null) {
                return getInstance().objectMapper.readValue(json,
                        getInstance().objectMapper.getTypeFactory().constructCollectionType(List.class, BookingHistory.class));
            }
            return new ArrayList<>();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing booking history", e);
        }
    }

    public static void removeBookingHistory(String userId) {
        String key = BOOKING_HISTORY_CACHE + ":" + userId;
        getInstance().cache.remove(key);
    }

    public static void clearBookingHistory() {
        getInstance().cache.clear();
    }

    // Hotel Metadata Methods
    public static void addHotelMetadata(HotelMetadata metadata) {
        try {
            String json = getInstance().objectMapper.writeValueAsString(metadata);
            String key = HOTEL_METADATA_CACHE + ":" + metadata.getHotelCode();
            getInstance().cache.put(key, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing hotel metadata", e);
        }
    }

    public HotelMetadata getHotelMetadata(String hotelCode) {
        try {
            String key = HOTEL_METADATA_CACHE + ":" + hotelCode;
            String json = getInstance().cache.get(key);
            if (json != null) {
                return getInstance().objectMapper.readValue(json, HotelMetadata.class);
            }
            return null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing hotel metadata", e);
        }
    }

    public static void removeHotelMetadata(String hotelCode) {
        String key = HOTEL_METADATA_CACHE + ":" + hotelCode;
        getInstance().cache.remove(key);
    }

    public static void clearAll() {
        getInstance().cache.clear();
    }

    public static void publishPacketToQueue(BookingRequest bookingRequest) {
        BookingHistory bookingHistory = new BookingHistory();
        bookingHistory.setUserId(bookingRequest.getUserId());
        bookingHistory.setBookingId(bookingRequest.getBookingId());
        bookingHistory.setCreatedAt(LocalDateTime.now());
        bookingHistory.setBookingStatus(HotelDetails_Enum.BookingStatus.CONFIRMED);
        addBookingHistory(bookingHistory);
    }

    public static void addCancellationPoliciesToCache(String bookingId, List<CancellationPolicy> policies) {
        try {
            String json = getInstance().objectMapper.writeValueAsString(policies);
            String key = CANCELLATION_POLICY_CACHE + ":" + bookingId;
            getInstance().cache.put(key, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing cancellation policies", e);
        }
    }

    public static List<CancellationPolicy> getCancellationPoliciesFromCache(String bookingId) {
        try {
            String key = CANCELLATION_POLICY_CACHE + ":" + bookingId;
            String json = getInstance().cache.get(key);
            if (json != null) {
                return getInstance().objectMapper.readValue(json,
                        getInstance().objectMapper.getTypeFactory().constructCollectionType(List.class, CancellationPolicy.class));
            }
            return null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing cancellation policies", e);
        }
    }

    public static ObjectMapper getObjectMapper() {
        return getInstance().objectMapper;
    }

    public static RestTemplate getRestTemplate() {
        return getInstance().restTemplate;
    }
}
