package com.hrs.hotelbooking.service.impl;

import com.hrs.hotelbooking.model.Hotel;
import com.hrs.hotelbooking.model.HotelMetadata;
import com.hrs.hotelbooking.model.SearchRequest;
import com.hrs.hotelbooking.model.SearchResponse;
import com.hrs.hotelbooking.repository.HotelRepository;
import com.hrs.hotelbooking.service.SearchService;
import com.hrs.hotelbooking.utils.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private CacheUtil cacheUtil;

    @Override
    public List<SearchResponse> searchHotels(SearchRequest request) {
        List<Hotel> hotels = hotelRepository.findAll();

        return hotels.stream()
                .filter(hotel -> filterByCity(hotel, request.getCity()))
                .filter(hotel -> hotel.getStatus() == 1)
                .map(this::createSearchResponse)
                .collect(Collectors.toList());
    }

    /**
     * Create search response for a hotel
     *
     * @param hotel
     * @return
     */
    private SearchResponse createSearchResponse(Hotel hotel) {
        // In first call make hotelier third Party api call and do cache in Aerospike with TTL 1 hour
        // In next call check cache and return from cache if available else make third party api call and update cache
        HotelMetadata metadata = cacheUtil.getHotelMetadata(hotel.getCode());

        return new SearchResponse(
                hotel.getCode(),
                hotel.getName(),
                hotel.getCity(),
                hotel.getCategory(),
                metadata != null ? metadata.getBasePrice() : 0.0,
                metadata != null ? metadata.getImageUrls() : List.of(),
                metadata != null ? metadata.getDescription() : "",
                metadata != null ? metadata.getAmenities() : List.of(),
                metadata != null ? metadata.getRating() : 0.0,
                metadata != null ? metadata.getTotalReviews() : 0,
                metadata != null ? metadata.getLocation() : "",
                metadata != null ? metadata.getCheckInTime() : "",
                metadata != null ? metadata.getCheckOutTime() : "",
                true
        );
    }

    private boolean filterByCity(Hotel hotel, String city) {
        return city == null || hotel.getCity().equalsIgnoreCase(city);
    }

    private boolean filterByStatus(Hotel hotel) {
        return hotel.getStatus() == 1; // 1 is active status
    }
} 