package com.brichev.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Stock {
    private final int id;
    private final String name;
    private double price;
    private int amount;
    

    public void updatePrice(double delta) {
        price += delta;
    }

    public void updateAmount(int delta) {
        amount += delta;
    }
}
