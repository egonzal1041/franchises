package com.franchises.franchises.service;

import com.franchises.franchises.entity.Franchise;
import com.franchises.franchises.repository.FranchiseRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FranchiseService {

    private final FranchiseRepository repository;

    public FranchiseService(FranchiseRepository repository) {
        this.repository = repository;
    }

    public Flux<Franchise> getAll() {
        return repository.findAll();
    }

    public Mono<Franchise> getById(String id) {
        return repository.findById(id);
    }

    public Mono<Franchise> save(Franchise franchise) {
        return repository.save(franchise);
    }

    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }

}
