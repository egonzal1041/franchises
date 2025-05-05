package com.franchises.franchises.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Franchise {
    @Id
    private String id;
    private String name;
    private List<Branch> branches;
}
