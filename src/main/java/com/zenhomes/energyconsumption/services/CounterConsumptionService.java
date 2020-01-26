package com.zenhomes.energyconsumption.services;

import com.zenhomes.energyconsumption.gateways.CounterGateway;
import com.zenhomes.energyconsumption.models.CounterConsumption;
import com.zenhomes.energyconsumption.models.dto.VillageConsumption;
import com.zenhomes.energyconsumption.models.dto.VillageConsumptions;
import com.zenhomes.energyconsumption.repositories.CounterConsumptionRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
public class CounterConsumptionService {

    private final CounterGateway counterGateway;
    private final CounterConsumptionRepository counterConsumptionRepository;

    public CounterConsumptionService(CounterGateway counterGateway, CounterConsumptionRepository counterConsumptionRepository) {
        this.counterGateway = counterGateway;
        this.counterConsumptionRepository = counterConsumptionRepository;
    }

    public CounterConsumption createConsumptionRecord(CounterConsumption counterConsumption) {
        return counterConsumptionRepository.insert(counterConsumption);
    }

    public VillageConsumptions calculateVillageConsumptions(Date from) {
        final var allCounterConsumptionStatistics = counterConsumptionRepository.findCounterConsumptionStatistics(from);
        final var villageConsumptions = allCounterConsumptionStatistics.parallelStream()
                .map(counterConsumptionStatistics -> {
                    final var counterId = counterConsumptionStatistics.getCounterId();
                    final var counter = counterGateway.getCounter(counterId);
                    return new VillageConsumption(counter.getVillageName(), counterConsumptionStatistics.getEnergyConsumed());
                }).collect(Collectors.toCollection(VillageConsumptions::new));

        return villageConsumptions.distinctByVillageNames();
    }
}