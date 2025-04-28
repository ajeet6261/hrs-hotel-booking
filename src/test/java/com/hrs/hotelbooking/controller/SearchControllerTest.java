package com.hrs.hotelbooking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrs.hotelbooking.model.SearchRequest;
import com.hrs.hotelbooking.model.SearchResponse;
import com.hrs.hotelbooking.service.SearchService;
import com.hrs.hotelbooking.service.impl.SearchServiceImpl;
import com.hrs.hotelbooking.utils.CacheUtil;
import com.hrs.hotelbooking.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SearchController.class)
class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SearchServiceImpl searchService;

    @Autowired
    private CacheUtil cacheUtil;

    @BeforeEach
    void setUp() {
        // Initialize cache with test data using TestUtil
        TestUtil.initializeHotelMetadataCache(cacheUtil);
    }

    @Test
    void searchHotels_ShouldReturnHotels() throws Exception {
        // Arrange
        SearchRequest request = new SearchRequest();
        request.setCity("Mumbai");

        List<SearchResponse> expectedResponse = Arrays.asList(
            createSearchResponse("HOTEL001", "Taj Mahal", "Mumbai", "5-star", 10000.0),
            createSearchResponse("HOTEL002", "Oberoi", "Mumbai", "5-star", 12000.0)
        );

        when(searchService.searchHotels(any(SearchRequest.class)))
            .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        // Act & Assert
        mockMvc.perform(post("/api/search/hotels")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"city\":\"Mumbai\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].hotelCode").value("HOTEL001"))
                .andExpect(jsonPath("$[0].name").value("Taj Mahal"))
                .andExpect(jsonPath("$[0].city").value("Mumbai"))
                .andExpect(jsonPath("$[0].category").value("5-star"))
                .andExpect(jsonPath("$[0].basePrice").value(10000.0))
                .andExpect(jsonPath("$[1].hotelCode").value("HOTEL002"))
                .andExpect(jsonPath("$[1].name").value("Oberoi"))
                .andExpect(jsonPath("$[1].city").value("Mumbai"))
                .andExpect(jsonPath("$[1].category").value("5-star"))
                .andExpect(jsonPath("$[1].basePrice").value(12000.0));
    }

    @Test
    void searchHotels_WhenServiceFails_ShouldReturnError() throws Exception {
        // Arrange
        when(searchService.searchHotels(any(SearchRequest.class)))
            .thenReturn(CompletableFuture.failedFuture(new RuntimeException("Service error")));

        // Act & Assert
        mockMvc.perform(post("/api/search/hotels")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"city\":\"Mumbai\"}"))
                .andExpect(status().isInternalServerError());
    }

    private SearchResponse createSearchResponse(String hotelCode, String name, String city, String category, double basePrice) {
        SearchResponse response = new SearchResponse();
        response.setHotelCode(hotelCode);
        response.setName(name);
        response.setCity(city);
        response.setCategory(category);
        response.setBasePrice(basePrice);
        return response;
    }
} 