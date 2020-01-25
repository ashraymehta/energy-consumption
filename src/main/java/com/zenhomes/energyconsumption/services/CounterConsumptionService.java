package com.zenhomes.energyconsumption.services;

import com.zenhomes.energyconsumption.models.CounterConsumption;
import com.zenhomes.energyconsumption.repositories.CounterConsumptionRepository;
import org.springframework.stereotype.Service;

@Service
public class CounterConsumptionService {

    private final CounterConsumptionRepository counterConsumptionRepository;

    public CounterConsumptionService(CounterConsumptionRepository counterConsumptionRepository) {
        this.counterConsumptionRepository = counterConsumptionRepository;
    }

    public CounterConsumption createConsumptionRecord(CounterConsumption counterConsumption) {
        return counterConsumptionRepository.insert(counterConsumption);
    }

}