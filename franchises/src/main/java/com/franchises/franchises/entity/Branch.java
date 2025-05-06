package com.franchises.franchises.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class Branch {
    private String name;
    private List<Product> products;
}
