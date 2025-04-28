package com.hrs.hotelbooking.controller;

import com.hrs.hotelbooking.model.SearchRequest;
import com.hrs.hotelbooking.model.SearchResponse;
import com.hrs.hotelbooking.service.impl.SearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchServiceImpl searchService;

    @PostMapping("/hotels")
    @Async("taskExecutor")
    public CompletableFuture<ResponseEntity<List<SearchResponse>>> searchHotels(@RequestBody SearchRequest request) {
        // Capture the request context
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        
        return searchService.searchHotels(request)
            .thenApply(response -> {
                // Restore the request context in the async thread
                if (attributes != null) {
                    RequestContextHolder.setRequestAttributes(attributes, true);
                }
                return ResponseEntity.ok(response);
            });
    }
}
