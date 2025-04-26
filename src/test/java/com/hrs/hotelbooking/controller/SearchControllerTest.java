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
    void testSearchHotels() throws Exception {
        // Create search request
        SearchRequest request = new SearchRequest();
        request.setCity("Mumbai");
        request.setCheckInDate(LocalDate.parse("2024-04-01"));
        request.setCheckOutDate(LocalDate.parse("2024-04-05"));

        // Create expected response using TestUtil
        List<SearchResponse> expectedResponse = Arrays.asList(
            TestUtil.createTajMahalResponse(),
            TestUtil.createOberoiResponse()
        );

        // Mock service response
        when(searchService.searchHotels(any(SearchRequest.class))).thenReturn(expectedResponse);

        // Perform test
        mockMvc.perform(post("/api/search/hotels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].hotelCode").value("TAJ001"))
                .andExpect(jsonPath("$[0].name").value("Taj Mahal Palace"))
                .andExpect(jsonPath("$[0].city").value("Mumbai"))
                .andExpect(jsonPath("$[0].category").value("5-star"))
                .andExpect(jsonPath("$[0].basePrice").value(10000.0))
                .andExpect(jsonPath("$[0].rating").value(4.8))
                .andExpect(jsonPath("$[1].hotelCode").value("OBR002"))
                .andExpect(jsonPath("$[1].name").value("The Oberoi"))
                .andExpect(jsonPath("$[1].city").value("Mumbai"))
                .andExpect(jsonPath("$[1].category").value("5-star"))
                .andExpect(jsonPath("$[1].basePrice").value(15000.0))
                .andExpect(jsonPath("$[1].rating").value(4.9));
    }

    @Test
    void testSearchHotelsWithInvalidRequest() throws Exception {
        // Create invalid search request (missing required fields)
        SearchRequest request = new SearchRequest();
        request.setCity("Mumbai");
        // Missing checkInDate and checkOutDate

        // Perform test
        mockMvc.perform(post("/api/search/hotels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
} 