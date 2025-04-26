package com.hrs.hotelbooking.service;

import com.hrs.hotelbooking.model.SearchRequest;
import com.hrs.hotelbooking.model.SearchResponse;
import java.util.List;

public interface SearchService {
    List<SearchResponse> searchHotels(SearchRequest request);
} 