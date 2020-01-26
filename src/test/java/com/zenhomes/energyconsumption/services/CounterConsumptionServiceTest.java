package com.zenhomes.energyconsumption.services;

import com.zenhomes.energyconsumption.gateways.CounterGateway;
import com.zenhomes.energyconsumption.models.CounterConsumption;
import com.zenhomes.energyconsumption.models.dto.Counter;
import com.zenhomes.energyconsumption.models.dto.CounterConsumptionStatistics;
import com.zenhomes.energyconsumption.models.dto.VillageConsumption;
import com.zenhomes.energyconsumption.repositories.CounterConsumptionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CounterConsumptionServiceTest {

    @InjectMocks
    private CounterConsumptionService counterConsumptionService;

    @Mock
    private CounterConsumptionRepository counterConsumptionRepository;

    @Mock
    private CounterGateway counterGateway;

    @Test
    void shouldInvokeRepositoryToCreateCounterConsumption() {
        final var counterConsumptionToBeSaved = new CounterConsumption("1", 2000.00);

        counterConsumptionService.createConsumptionRecord(counterConsumptionToBeSaved);

        verify(counterConsumptionRepository).insert(counterConsumptionToBeSaved);
    }

    @Test
    void shouldCalculateVillageConsumptionsForEachCounter() {
//      given
        final var fromDate = new Date();
        final var aCounterId = "1";
        final var anotherCounterId = "2";
        final var aVillageName = "Villarriba";
        final var anotherVillageName = "Villabajo";
        final var counterConsumptionStatistics = List.of(
                new CounterConsumptionStatistics(aCounterId, 2000.00),
                new CounterConsumptionStatistics(anotherCounterId, 5000.00)
        );
        when(counterConsumptionRepository.findCounterConsumptionStatistics(fromDate)).thenReturn(counterConsumptionStatistics);
        when(counterGateway.getCounter(aCounterId)).thenReturn(new Counter(aCounterId, aVillageName));
        when(counterGateway.getCounter(anotherCounterId)).thenReturn(new Counter(anotherCounterId, anotherVillageName));

//      when
        final var villageConsumptions = counterConsumptionService.calculateVillageConsumptions(fromDate);

//      then
        final var anExpectedVillageConsumption = new VillageConsumption(aVillageName, 2000.00);
        final var anotherExpectedVillageConsumption = new VillageConsumption(anotherVillageName, 5000.00);
        assertThat(villageConsumptions, containsInAnyOrder(anExpectedVillageConsumption,
                anotherExpectedVillageConsumption));
    }

    @Test
    void shouldAggregateConsumptionFromMultipleCountersForTheSameVillage() {
//      given
        final var fromDate = new Date();
        final var aCounterId = "1";
        final var anotherCounterId = "2";
        final var aVillageName = "Villarriba";
        final var anotherVillageName = "Villabajo";
        final var counterConsumptionStatistics = List.of(
                new CounterConsumptionStatistics(aCounterId, 2000.00),
                new CounterConsumptionStatistics(aCounterId, 1000.00),
                new CounterConsumptionStatistics(aCounterId, 500.00),
                new CounterConsumptionStatistics(anotherCounterId, 5000.00)
        );
        when(counterConsumptionRepository.findCounterConsumptionStatistics(fromDate)).thenReturn(counterConsumptionStatistics);
        when(counterGateway.getCounter(aCounterId)).thenReturn(new Counter(aCounterId, aVillageName));
        when(counterGateway.getCounter(anotherCounterId)).thenReturn(new Counter(anotherCounterId, anotherVillageName));

//      when
        final var villageConsumptions = counterConsumptionService.calculateVillageConsumptions(fromDate);

//      then
        final var anExpectedVillageConsumption = new VillageConsumption(aVillageName, 3500.00);
        final var anotherExpectedVillageConsumption = new VillageConsumption(anotherVillageName, 5000.00);
        assertThat(villageConsumptions, hasSize(2));
        assertThat(villageConsumptions, containsInAnyOrder(anExpectedVillageConsumption,
                anotherExpectedVillageConsumption));
    }
}