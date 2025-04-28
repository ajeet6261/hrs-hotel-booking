package com.hrs.hotelbooking.service.impl;

import com.hrs.hotelbooking.model.*;
import com.hrs.hotelbooking.repository.CancellationDetailsRepository;
import com.hrs.hotelbooking.repository.HotelDetailsRepository;
import com.hrs.hotelbooking.repository.HotelRepository;
import com.hrs.hotelbooking.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceContextServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(ServiceContextServiceImpl.class);

    @Autowired
    private HotelDetailsRepository hotelDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private CancellationDetailsRepository cancellationDetailsRepository;

    public ServiceContext createServiceContext(String bookingId, String hotelCode, String userId) {
        logger.info("Creating service context for bookingId: {}, hotelCode: {}, userId: {}", 
            bookingId, hotelCode, userId);
            
        ServiceContext serviceContext = new ServiceContext();
        Hotel hotel = null;
        List<HotelDetails> hotelDetails = null;
        List<CancellationDetails> cancellationDetails = null;
        
        if (StringUtils.isNotEmpty(bookingId)) {
            logger.debug("Fetching hotel details for bookingId: {}", bookingId);
            hotelDetails = hotelDetailsRepository.findByBookingId(bookingId);
            if (!hotelDetails.isEmpty()) {
                hotelCode = hotelDetails.getFirst().getHotelCode();
                logger.debug("Found hotelCode: {} for bookingId: {}", hotelCode, bookingId);
            }
            
            logger.debug("Fetching hotel information for hotelCode: {}", hotelCode);
            hotel = hotelRepository.findByCode(hotelCode);
            
            logger.debug("Fetching cancellation details for bookingId: {}", bookingId);
            cancellationDetails = cancellationDetailsRepository.findByBookingId(bookingId);
        }

        User user = null;
        if (StringUtils.isNotEmpty(userId)) {
            logger.debug("Fetching user information for userId: {}", userId);
            user = userRepository.findByUserId(userId);
        }
        
        Booking booking = new Booking();
        booking.setBookingId(bookingId);
        booking.setUser(user);
        booking.setHotel(hotel);
        booking.setHotelDetails(hotelDetails);
        booking.setCancellationDetails(cancellationDetails);
        serviceContext.setBooking(booking);
        
        logger.info("Service context created successfully");
        return serviceContext;
    }
}
