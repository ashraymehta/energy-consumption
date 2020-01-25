package com.zenhomes.energyconsumption.controllers;

import com.zenhomes.energyconsumption.models.CounterConsumption;
import com.zenhomes.energyconsumption.services.CounterConsumptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CounterConsumptionController {

    private final CounterConsumptionService counterConsumptionService;

    public CounterConsumptionController(CounterConsumptionService counterConsumptionService) {
        this.counterConsumptionService = counterConsumptionService;
    }

    @PostMapping("counter_callback")
    public ResponseEntity<Void> createCounterConsumption(@RequestBody @Valid CounterConsumption counterConsumption) {
        counterConsumptionService.createConsumptionRecord(counterConsumption);
        return ResponseEntity.noContent().build();
    }

}