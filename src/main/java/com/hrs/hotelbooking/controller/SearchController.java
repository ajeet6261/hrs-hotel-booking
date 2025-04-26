package com.hrs.hotelbooking.controller;

import com.hrs.hotelbooking.model.SearchRequest;
import com.hrs.hotelbooking.model.SearchResponse;
import com.hrs.hotelbooking.service.impl.SearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchServiceImpl searchService;

    @PostMapping("/hotels")
    public ResponseEntity<List<SearchResponse>> searchHotels(@RequestBody SearchRequest request) {
        List<SearchResponse> searchHotels = searchService.searchHotels(request);
        return ResponseEntity.ok(searchHotels);
    }
}
