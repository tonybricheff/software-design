package com.brichev.model;

public enum Currency {
    RUB(1.0),
    EUR(0.007),
    USD(0.008);

    double cost;

    Currency(double cost) {
        this.cost = cost;
    }

    public double convert(Currency to, Double value) {
        return value * to.cost / cost;
    }
}
