package com.zenhomes.energyconsumption.models;

public class CounterConsumption extends BaseModel {
    private final String counterId;
    private final double amount;

    public CounterConsumption(String counterId, double amount) {
        this.counterId = counterId;
        this.amount = amount;
    }
}