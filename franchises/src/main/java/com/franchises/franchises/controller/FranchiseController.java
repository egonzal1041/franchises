package com.franchises.franchises.controller;

import com.franchises.franchises.entity.Branch;
import com.franchises.franchises.entity.Franchise;
import com.franchises.franchises.entity.Product;
import com.franchises.franchises.service.IFranchiseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@AllArgsConstructor
@RestController
@RequestMapping("/api/franchises")
public class FranchiseController {

    private IFranchiseService service;

    @GetMapping
    public Flux<Franchise> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Mono<Franchise> getById(@PathVariable String id) {
        return service.getById(id);
    }

    @PostMapping
    public Mono<ResponseEntity<Franchise>> create(@RequestBody Franchise franchise) {
        return service.save(franchise)
                .map(saved -> ResponseEntity.ok(saved));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return service.delete(id);
    }

    // 1. Agregar una nueva sucursal a una franquicia
    @PostMapping("/{franchiseId}/branches")
    public Mono<ResponseEntity<Franchise>> addBranch(
            @PathVariable String franchiseId,
            @RequestBody Branch newBranch) {

        return service.getById(franchiseId)
                .flatMap(franchise -> {
                    if (franchise.getBranches() == null) {
                        franchise.setBranches(new ArrayList<>());
                    }
                    franchise.getBranches().add(newBranch);
                    return service.save(franchise);
                })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // 2. Agregar un nuevo producto a una sucursal
    @PostMapping("/{franchiseId}/branches/{branchName}/products")
    public Mono<ResponseEntity<?>> addProductToBranch(
            @PathVariable String franchiseId,
            @PathVariable String branchName,
            @RequestBody Product product) {

        return service.getById(franchiseId)
                .flatMap(franchise -> {
                    var branchOpt = franchise.getBranches().stream()
                            .filter(b -> b.getName().equalsIgnoreCase(branchName))
                            .findFirst();

                    if (branchOpt.isPresent()) {
                        Branch branch = branchOpt.get();
                        if (branch.getProducts() == null) {
                            branch.setProducts(new ArrayList<>());
                        }
                        branch.getProducts().add(product);
                        return service.save(franchise).map(ResponseEntity::ok);
                    } else {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // 3. Eliminar un producto de una sucursal
    @DeleteMapping("/{franchiseId}/branches/{branchName}/products/{productName}")
    public Mono<ResponseEntity<?>> removeProductFromBranch(
            @PathVariable String franchiseId,
            @PathVariable String branchName,
            @PathVariable String productName) {

        return service.getById(franchiseId)
                .flatMap(franchise -> {
                    var branchOpt = franchise.getBranches().stream()
                            .filter(b -> b.getName().equalsIgnoreCase(branchName))
                            .findFirst();

                    if (branchOpt.isPresent()) {
                        Branch branch = branchOpt.get();
                        boolean removed = branch.getProducts().removeIf(p -> p.getName().equalsIgnoreCase(productName));
                        if (removed) {
                            return service.save(franchise).map(ResponseEntity::ok);
                        }
                    }
                    return Mono.just(ResponseEntity.notFound().build());
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // 4. Modificar stock de un producto
    @PutMapping("/{franchiseId}/branches/{branchName}/products/{productName}/stock")
    public Mono<ResponseEntity<?>> updateStock(
            @PathVariable String franchiseId,
            @PathVariable String branchName,
            @PathVariable String productName,
            @RequestParam int stock) {

        return service.getById(franchiseId)
                .flatMap(franchise -> {
                    var branchOpt = franchise.getBranches().stream()
                            .filter(b -> b.getName().equalsIgnoreCase(branchName))
                            .findFirst();

                    if (branchOpt.isPresent()) {
                        Branch branch = branchOpt.get();
                        var productOpt = branch.getProducts().stream()
                                .filter(p -> p.getName().equalsIgnoreCase(productName))
                                .findFirst();

                        if (productOpt.isPresent()) {
                            productOpt.get().setStock(stock);
                            return service.save(franchise).map(ResponseEntity::ok);
                        }
                    }
                    return Mono.just(ResponseEntity.notFound().build());
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
