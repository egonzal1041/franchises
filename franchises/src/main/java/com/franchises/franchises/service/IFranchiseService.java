package com.franchises.franchises.service;

import com.franchises.franchises.dto.TopProductDTO;
import com.franchises.franchises.entity.Franchise;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IFranchiseService {

    Flux<Franchise> getAll();
    Mono<Franchise> getById(String id);
    Mono<Franchise> save(Franchise franchise);
    Mono<Void> delete(String id);
    Mono<ResponseEntity<List<TopProductDTO>>> getTopProductsByBranch(String franchiseId);
}
