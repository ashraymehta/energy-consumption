package com.zenhomes.energyconsumption.services;

import com.zenhomes.energyconsumption.gateways.CounterGateway;
import com.zenhomes.energyconsumption.models.CounterConsumption;
import com.zenhomes.energyconsumption.models.dto.VillageConsumption;
import com.zenhomes.energyconsumption.repositories.CounterConsumptionRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

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

    public Collection<VillageConsumption> calculateVillageConsumptions(Date from) {
        final var allCounterConsumptionStatistics = counterConsumptionRepository.findCounterConsumptionStatistics(from);
        final var nonUniqueVillageConsumptions = allCounterConsumptionStatistics.stream()
                .map(counterConsumptionStatistics -> {
                    final var counterId = counterConsumptionStatistics.getCounterId();
                    final var counter = counterGateway.getCounter(counterId);
                    return new VillageConsumption(counter.getVillageName(), counterConsumptionStatistics.getEnergyConsumed());
                }).collect(Collectors.toSet());

        return nonUniqueVillageConsumptions.stream().collect(toMap(VillageConsumption::getVillageName, consumption -> consumption,
                (aVillageConsumption, anotherVillageConsumption) -> {
                    final var totalConsumption = aVillageConsumption.getConsumption() + anotherVillageConsumption.getConsumption();
                    return new VillageConsumption(aVillageConsumption.getVillageName(), totalConsumption);
                }))
                .values();
    }
}