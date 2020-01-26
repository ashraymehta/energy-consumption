package com.zenhomes.energyconsumption.models.dto;

import com.zenhomes.energyconsumption.models.BaseModel;

public class Counter extends BaseModel {
    private final String id;
    private final String villageName;

    public Counter(String id, String villageName) {
        this.id = id;
        this.villageName = villageName;
    }

    public String getVillageName() {
        return villageName;
    }
}