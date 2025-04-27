package com.hrs.hotelbooking.repository;

import com.hrs.hotelbooking.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, String> {
    Hotel findByCode(String hotelCode);
} 