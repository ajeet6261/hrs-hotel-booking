package com.hrs.hotelbooking.repository;

import com.hrs.hotelbooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
    User findByPhoneNumber(String phoneNumber);
    User findByUserId(String userId);
} 