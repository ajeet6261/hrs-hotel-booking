package com.hrs.hotelbooking.service;

import com.hrs.hotelbooking.model.SearchRequest;
import com.hrs.hotelbooking.model.SearchResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface SearchService {
    CompletableFuture<List<SearchResponse>> searchHotels(SearchRequest request);
} 