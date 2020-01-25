package com.zenhomes.energyconsumption.models.dto;

public class ConsumptionAggregationDTO {
    private final String counterId;
    private final Double total;

    public ConsumptionAggregationDTO(String counterId, Double total) {
        this.counterId = counterId;
        this.total = total;
    }

    public String getCounterId() {
        return counterId;
    }

    public Double getTotal() {
        return total;
    }

    public interface ClassFields {
        String COUNTER_ID = "counterId";
        String TOTAL = "total";
    }
}