package com.hrs.hotelbooking.repository;

import com.hrs.hotelbooking.model.CancellationDetails;
import com.hrs.hotelbooking.model.CancellationDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CancellationDetailsRepository extends JpaRepository<CancellationDetails, CancellationDetailsId> {
} 