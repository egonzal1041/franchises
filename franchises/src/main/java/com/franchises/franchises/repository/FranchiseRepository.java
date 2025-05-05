package com.franchises.franchises.repository;

import com.franchises.franchises.entity.Franchise;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface FranchiseRepository extends ReactiveMongoRepository<Franchise, String> {}
