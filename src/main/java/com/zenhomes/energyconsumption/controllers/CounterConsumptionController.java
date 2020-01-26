package com.zenhomes.energyconsumption.controllers;

import com.zenhomes.energyconsumption.models.CounterConsumption;
import com.zenhomes.energyconsumption.models.dto.ConsumptionReportResponse;
import com.zenhomes.energyconsumption.services.CounterConsumptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

@RestController
public class CounterConsumptionController {

    private final CounterConsumptionService counterConsumptionService;

    public CounterConsumptionController(CounterConsumptionService counterConsumptionService) {
        this.counterConsumptionService = counterConsumptionService;
    }

    @PostMapping("counter_callback")
    public ResponseEntity<CounterConsumption> createCounterConsumption(@RequestBody @Valid CounterConsumption counterConsumption) {
        final var createdConsumption = counterConsumptionService.createConsumptionRecord(counterConsumption);
        return new ResponseEntity<>(createdConsumption, HttpStatus.CREATED);
    }

    @GetMapping("consumption_report")
    public ResponseEntity<ConsumptionReportResponse> getConsumptionReport() {
        final var villageConsumptions = counterConsumptionService.calculateVillageConsumptions(new Date());
        return new ResponseEntity<>(new ConsumptionReportResponse(villageConsumptions), HttpStatus.OK);
    }

}