package com.brichev.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class User {
    private final int id;
    private final String name;
    private double balance;
    private final Map<Integer, Integer> stocks;


    public void updateBalance(double delta) {
        balance += delta;
    }
}
