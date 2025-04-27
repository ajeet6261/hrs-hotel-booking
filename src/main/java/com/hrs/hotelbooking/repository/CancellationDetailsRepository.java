package com.hrs.hotelbooking.repository;

import com.hrs.hotelbooking.model.CancellationDetails;
import com.hrs.hotelbooking.model.CancellationDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CancellationDetailsRepository extends JpaRepository<CancellationDetails, CancellationDetailsId> {
    List<CancellationDetails> findByBookingId(String bookingId);
} 