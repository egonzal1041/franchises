package com.franchises.franchises.service.implementation;

import com.franchises.franchises.dto.TopProductDTO;
import com.franchises.franchises.entity.Branch;
import com.franchises.franchises.entity.Franchise;
import com.franchises.franchises.entity.Product;
import com.franchises.franchises.repository.FranchiseRepository;
import com.franchises.franchises.service.IFranchiseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    @Override
    public Mono<ResponseEntity<List<TopProductDTO>>> getTopProductsByBranch(String franchiseId) {
        return repository.findById(franchiseId)
                .map(franchise -> {
                    List<TopProductDTO> topProducts = new ArrayList<>();

                    if (franchise.getBranches() != null) {
                        for (Branch branch : franchise.getBranches()) {
                            if (branch.getProducts() != null && !branch.getProducts().isEmpty()) {
                                Product topProduct = branch.getProducts().stream()
                                        .max(Comparator.comparingInt(Product::getStock))
                                        .orElse(null);

                                if (topProduct != null) {
                                    topProducts.add(new TopProductDTO(
                                            branch.getName(),
                                            topProduct.getName(),
                                            topProduct.getStock()
                                    ));
                                }
                            }
                        }
                    }

                    return ResponseEntity.ok(topProducts);
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
