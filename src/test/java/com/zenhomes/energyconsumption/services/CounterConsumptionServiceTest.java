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
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

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
        final var counterConsumptionToBeSaved = new CounterConsumption("1", 2000D);

        counterConsumptionService.createConsumptionRecord(counterConsumptionToBeSaved);

        verify(counterConsumptionRepository).insert(counterConsumptionToBeSaved);
    }

    @Test
    void shouldCalculateVillageConsumptionsForEachCounter() {
        final var fromDate = new Date();
        final var aCounterId = "1";
        final var anotherCounterId = "2";
        final var aVillageName = "Villarriba";
        final var anotherVillageName = "Villabajo";
        final var counterConsumptionStatistics = List.of(
                new CounterConsumptionStatistics(aCounterId, 2000D),
                new CounterConsumptionStatistics(anotherCounterId, 5000D)
        );
        when(counterConsumptionRepository.findCounterConsumptionStatistics(fromDate)).thenReturn(counterConsumptionStatistics);
        when(counterGateway.getCounter(aCounterId)).thenReturn(new Counter(aCounterId, aVillageName));
        when(counterGateway.getCounter(anotherCounterId)).thenReturn(new Counter(anotherCounterId, anotherVillageName));

        final var villageConsumptions = counterConsumptionService.calculateVillageConsumptions(fromDate);

        final var expectedVillageConsumptions = Set.of(new VillageConsumption(aVillageName, 2000D),
                new VillageConsumption(anotherVillageName, 5000D));
        assertThat(villageConsumptions, equalTo(expectedVillageConsumptions));
    }
}