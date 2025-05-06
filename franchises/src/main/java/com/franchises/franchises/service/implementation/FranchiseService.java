package com.franchises.franchises.service.implementation;

import com.franchises.franchises.entity.Franchise;
import com.franchises.franchises.repository.FranchiseRepository;
import com.franchises.franchises.service.IFranchiseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class FranchiseService implements IFranchiseService {

    private FranchiseRepository repository;

    @Override
    public Flux<Franchise> getAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Franchise> getById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        return repository.save(franchise);
    }

    @Override
    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }

}
