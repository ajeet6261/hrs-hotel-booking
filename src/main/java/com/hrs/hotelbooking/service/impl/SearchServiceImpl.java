package com.hrs.hotelbooking.service.impl;

import com.hrs.hotelbooking.model.Hotel;
import com.hrs.hotelbooking.model.HotelMetadata;
import com.hrs.hotelbooking.model.SearchRequest;
import com.hrs.hotelbooking.model.SearchResponse;
import com.hrs.hotelbooking.repository.HotelRepository;
import com.hrs.hotelbooking.service.SearchService;
import com.hrs.hotelbooking.utils.CacheUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    private static final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);
    
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private CacheUtil cacheUtil;

    @Autowired
    private ExecutorService executorService;

    @Override
    public CompletableFuture<List<SearchResponse>> searchHotels(SearchRequest request) {
        logger.info("Starting hotel search for city: {}", request.getCity());
        
        return CompletableFuture.supplyAsync(() -> {
            List<Hotel> hotels = hotelRepository.findAll();
            logger.info("Found {} hotels", hotels.size());
            
            return hotels.stream()
                    .filter(hotel -> filterByCity(hotel, request.getCity()))
                    .filter(hotel -> hotel.getStatus() == 1)
                    .map(this::createSearchResponse)
                    .collect(Collectors.toList());
        }, executorService);
    }

    /**
     * Create search response for a hotel
     *
     * @param hotel
     * @return
     */
    private SearchResponse createSearchResponse(Hotel hotel) {
        logger.debug("Creating search response for hotel: {}", hotel.getCode());
        
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
} 