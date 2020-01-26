package com.zenhomes.energyconsumption.models.dto;

public class VillageConsumption {
    private final String villageName;
    private final Double consumption;

    public VillageConsumption(String villageName, Double consumption) {
        this.villageName = villageName;
        this.consumption = consumption;
    }
}