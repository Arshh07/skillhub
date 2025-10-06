package com.skillhub.hub.service;

import org.springframework.stereotype.Service;

import com.skillhub.hub.model.Order;
import com.skillhub.hub.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository repo;

    public OrderService(OrderRepository repo) {
        this.repo = repo;
    }

    public Order create(Order order) { return repo.save(order); }
    public Optional<Order> findById(Long id) { return repo.findById(id); }
    public List<Order> findByClientId(Long clientId) { return repo.findByClientId(clientId); }
    public List<Order> findByFreelancerId(Long freelancerId) { return repo.findByFreelancerId(freelancerId); }
    public List<Order> all() { return repo.findAll(); }
}
