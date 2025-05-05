package com.franchises.franchises.controller;

import com.franchises.franchises.entity.Franchise;
import com.franchises.franchises.service.FranchiseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/api/franchises")
public class FranchiseController {

    private final FranchiseService service;

    @GetMapping
    public Flux<Franchise> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Mono<Franchise> getById(@PathVariable String id) {
        return service.getById(id);
    }

    @PostMapping
    public Mono<Franchise> create(@RequestBody Franchise franchise) {
        return service.save(franchise);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return service.delete(id);
    }
}
