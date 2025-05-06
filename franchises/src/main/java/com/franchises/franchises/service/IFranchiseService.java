package com.franchises.franchises.service;

import com.franchises.franchises.entity.Franchise;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IFranchiseService {

    Flux<Franchise> getAll();
    Mono<Franchise> getById(String id);
    Mono<Franchise> save(Franchise franchise);
    Mono<Void> delete(String id);
}
