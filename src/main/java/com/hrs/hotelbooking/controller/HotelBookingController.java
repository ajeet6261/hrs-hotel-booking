package com.hrs.hotelbooking.controller;

import com.hrs.hotelbooking.model.Request;
import com.hrs.hotelbooking.model.Response;
import com.hrs.hotelbooking.service.HotelService;
import com.hrs.hotelbooking.service.HotelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HotelBookingController {
    @Autowired
    HotelServiceImpl hotelService;

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public Response register(@RequestBody Request request) {
        return new Response();
    }
}
