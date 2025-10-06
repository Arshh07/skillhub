package com.skillhub.hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillhub.hub.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByClientId(Long clientId);
    List<Order> findByFreelancerId(Long freelancerId);
}
