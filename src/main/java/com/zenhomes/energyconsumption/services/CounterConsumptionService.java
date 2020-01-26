package com.zenhomes.energyconsumption.services;

import com.zenhomes.energyconsumption.gateways.CounterGateway;
import com.zenhomes.energyconsumption.models.CounterConsumption;
import com.zenhomes.energyconsumption.models.dto.VillageConsumption;
import com.zenhomes.energyconsumption.repositories.CounterConsumptionRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

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
        final var counterConsumptionStatistics = counterConsumptionRepository.findCounterConsumptionStatistics(from);
        counterConsumptionStatistics.forEach(consumption -> counterGateway.getCounter(consumption.getCounterId()));
        return null;
    }
}