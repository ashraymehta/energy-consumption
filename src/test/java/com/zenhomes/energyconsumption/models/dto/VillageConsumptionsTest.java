package com.zenhomes.energyconsumption.models.dto;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

class VillageConsumptionsTest {

    @Test
    void shouldCreateADistinctCollectionWithMultipleEntriesForTheSameVillageSummedUp() {
//      given
        final var aVillageName = "a-village";
        final var anotherVillageName = "another-village";
        final var villageConsumptions = VillageConsumptions.of(new VillageConsumption(aVillageName, 87.12),
                new VillageConsumption(anotherVillageName, 50.00),
                new VillageConsumption(aVillageName, 12.88),
                new VillageConsumption(aVillageName, 20.00));

//      when
        final var mergedVillageConsumptions = villageConsumptions.distinctByVillageNames();

//      then
        final var anExpectedVillageConsumption = new VillageConsumption(aVillageName, 120.00);
        final var anotherExpectedVillageConsumption = new VillageConsumption(anotherVillageName, 50.00);
        assertThat(mergedVillageConsumptions, hasSize(2));
        assertThat(mergedVillageConsumptions, containsInAnyOrder(anExpectedVillageConsumption,
                anotherExpectedVillageConsumption));
    }

    @Test
    void shouldReturnANewCollectionWhenConsolidatingEntriesByVillageName() {
//      given
        final var aVillageName = "a-village";
        final var anotherVillageName = "another-village";
        final var villageConsumptions = VillageConsumptions.of(new VillageConsumption(aVillageName, 87.12),
                new VillageConsumption(anotherVillageName, 50.00),
                new VillageConsumption(aVillageName, 12.88),
                new VillageConsumption(aVillageName, 20.00));

//      when
        final var mergedVillageConsumptions = villageConsumptions.distinctByVillageNames();

//      then
        assertThat(villageConsumptions, hasSize(4));
        assertThat(mergedVillageConsumptions, hasSize(2));
    }
}