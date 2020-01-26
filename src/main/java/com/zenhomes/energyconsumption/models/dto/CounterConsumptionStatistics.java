package com.zenhomes.energyconsumption.models.dto;

import com.zenhomes.energyconsumption.models.BaseModel;

public class CounterConsumptionStatistics extends BaseModel {
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