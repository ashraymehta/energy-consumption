package com.zenhomes.energyconsumption.services;

import com.zenhomes.energyconsumption.models.CounterConsumption;
import com.zenhomes.energyconsumption.repositories.CounterConsumptionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CounterConsumptionServiceTest {

    @InjectMocks
    private CounterConsumptionService counterConsumptionService;

    @Mock
    private CounterConsumptionRepository counterConsumptionRepository;

    @Test
    void shouldInvokeRepositoryToCreateCounterConsumption() {
        final var counterConsumptionToBeSaved = new CounterConsumption("1", 2000);

        counterConsumptionService.createConsumptionRecord(counterConsumptionToBeSaved);

        verify(counterConsumptionRepository).insert(counterConsumptionToBeSaved);
    }
}