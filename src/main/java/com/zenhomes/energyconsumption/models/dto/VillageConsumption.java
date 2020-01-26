package com.zenhomes.energyconsumption.models.dto;

import com.zenhomes.energyconsumption.models.BaseModel;

public class VillageConsumption extends BaseModel {
    private final String villageName;
    private final Double consumption;

    public VillageConsumption(String villageName, Double consumption) {
        this.villageName = villageName;
        this.consumption = consumption;
    }
}