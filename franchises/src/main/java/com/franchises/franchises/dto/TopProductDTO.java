package com.franchises.franchises.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopProductDTO {
    private String branch;
    private String productName;
    private int stock;
}
