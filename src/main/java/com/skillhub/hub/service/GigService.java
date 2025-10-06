package com.skillhub.hub.service;

import org.springframework.stereotype.Service;

import com.skillhub.hub.model.Gig;
import com.skillhub.hub.repository.GigRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GigService {
    private final GigRepository repo;

    public GigService(GigRepository repo) {
        this.repo = repo;
    }

    public Gig create(Gig gig) { return repo.save(gig); }
    public List<Gig> all() { return repo.findAll(); }
    public Optional<Gig> findById(Long id) { return repo.findById(id); }
    public List<Gig> search(String q) { return repo.findByTitleContainingIgnoreCase(q); }
    public void delete(Long id) { repo.deleteById(id); }
}
