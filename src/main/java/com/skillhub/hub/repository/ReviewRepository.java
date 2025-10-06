package com.skillhub.hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillhub.hub.model.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByFreelancerId(Long freelancerId);
}
