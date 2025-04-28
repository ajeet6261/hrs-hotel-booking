package com.hrs.hotelbooking.repository;

import com.hrs.hotelbooking.model.HotelDetails;
import com.hrs.hotelbooking.model.HotelDetailsId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelDetailsRepository extends JpaRepository<HotelDetails, HotelDetailsId> {
    List<HotelDetails> findByBookingId(String bookingId);
    boolean existsByBookingId(String bookingId);
} 