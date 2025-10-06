package com.skillhub.hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillhub.hub.model.Gig;

import java.util.List;

public interface GigRepository extends JpaRepository<Gig, Long> {
    List<Gig> findByTitleContainingIgnoreCase(String q);
    List<Gig> findByCategory(String category);
}
