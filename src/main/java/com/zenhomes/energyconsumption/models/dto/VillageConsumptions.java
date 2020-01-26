package com.zenhomes.energyconsumption.models.dto;

import java.util.Collection;
import java.util.HashSet;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toMap;

public class VillageConsumptions extends HashSet<VillageConsumption> {

    public VillageConsumptions() {
    }

    public VillageConsumptions(Collection<? extends VillageConsumption> c) {
        super(c);
    }

    public VillageConsumptions distinctByVillageNames() {
        final var mergedVillageConsumption = stream().collect(toMap(VillageConsumption::getVillageName,
                consumption -> consumption, (aVillageConsumption, anotherVillageConsumption) -> {
                    final var totalConsumption = aVillageConsumption.getConsumption() + anotherVillageConsumption.getConsumption();
                    return new VillageConsumption(aVillageConsumption.getVillageName(), totalConsumption);
                })).values();

        return new VillageConsumptions(mergedVillageConsumption);
    }

    public static VillageConsumptions of(VillageConsumption... villageConsumptions) {
        return new VillageConsumptions(asList(villageConsumptions));
    }
}