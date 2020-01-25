package com.zenhomes.energyconsumption.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class CounterConsumption extends BaseModel {

    @NotNull
    private final String counterId;

    @NotNull
    @PositiveOrZero
    private final Double amount;

    public CounterConsumption(String counterId, Double amount) {
        this.counterId = counterId;
        this.amount = amount;
    }

}