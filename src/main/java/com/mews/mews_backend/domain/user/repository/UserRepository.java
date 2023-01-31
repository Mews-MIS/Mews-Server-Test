package com.mews.mews_backend.domain.user.repository;

import com.mews.mews_backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Boolean existsByUserEmail(String email);
    Optional<User> findByUserEmail(String email);
}