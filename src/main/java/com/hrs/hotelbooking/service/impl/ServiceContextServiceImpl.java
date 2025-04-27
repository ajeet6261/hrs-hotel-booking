package com.hrs.hotelbooking.service.impl;

import com.hrs.hotelbooking.model.*;
import com.hrs.hotelbooking.repository.CancellationDetailsRepository;
import com.hrs.hotelbooking.repository.HotelDetailsRepository;
import com.hrs.hotelbooking.repository.HotelRepository;
import com.hrs.hotelbooking.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceContextServiceImpl {

    @Autowired
    private HotelDetailsRepository hotelDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private CancellationDetailsRepository cancellationDetailsRepository;

    public ServiceContext createServiceContext(String bookingId, String hotelCode, String userId) {
        ServiceContext serviceContext = new ServiceContext();
        Hotel hotel = null;
        List<HotelDetails> hotelDetails = null;
        List<CancellationDetails> cancellationDetails = null;
        if (StringUtils.isNotEmpty(bookingId)) {
            hotelDetails = hotelDetailsRepository.findByBookingId(bookingId);
            hotelCode = hotelDetails.getFirst().getHotelCode();
            hotel = hotelRepository.findByCode(hotelCode);
            cancellationDetails = cancellationDetailsRepository.findByBookingId(bookingId);
        }

        User user = null;
        if (StringUtils.isNotEmpty(userId)) {
            user = userRepository.findByUserId(userId);
        }
        Booking booking = new Booking();
        booking.setBookingId(bookingId);
        booking.setUser(user);
        booking.setHotel(hotel);
        booking.setHotelDetails(hotelDetails);
        booking.setCancellationDetails(cancellationDetails);
        serviceContext.setBooking(booking);
        return serviceContext;
    }
}
