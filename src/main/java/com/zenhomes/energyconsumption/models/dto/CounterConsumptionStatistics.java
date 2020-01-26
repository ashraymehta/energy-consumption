package com.zenhomes.energyconsumption.models.dto;

public class CounterConsumptionStatistics {
    private final String counterId;
    private final Double energyConsumed;

    public CounterConsumptionStatistics(String counterId, Double energyConsumed) {
        this.counterId = counterId;
        this.energyConsumed = energyConsumed;
    }

    public String getCounterId() {
        return counterId;
    }

    public Double getEnergyConsumed() {
        return energyConsumed;
    }

    public interface ClassFields {
        String COUNTER_ID = "counterId";
        String ENERGY_CONSUMED = "energyConsumed";
    }
}