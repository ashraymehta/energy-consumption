package com.zenhomes.energyconsumption.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class CounterConsumption {
    private final String counterId;
    private final double amount;

    public CounterConsumption(String counterId, double amount) {
        this.counterId = counterId;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}