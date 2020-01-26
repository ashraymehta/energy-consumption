package com.zenhomes.energyconsumption.models.dto;

import com.zenhomes.energyconsumption.models.BaseModel;

public class ConsumptionReportResponse extends BaseModel {

    private final VillageConsumptions villages;

    public ConsumptionReportResponse(VillageConsumptions villages) {
        this.villages = villages;
    }

}
