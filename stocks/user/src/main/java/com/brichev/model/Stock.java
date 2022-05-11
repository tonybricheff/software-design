package com.brichev.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Stock {
    private final int id;
    private final double price;
    private final int number;
}
