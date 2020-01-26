package com.zenhomes.energyconsumption.models.dto;

public class ConsumptionReportResponse {
    private final VillageConsumptions villages;

    public ConsumptionReportResponse(VillageConsumptions villages) {
        this.villages = villages;
    }
}
