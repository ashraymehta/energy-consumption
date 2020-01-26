package com.zenhomes.energyconsumption.controllers;

import com.zenhomes.energyconsumption.models.CounterConsumption;
import com.zenhomes.energyconsumption.models.dto.ConsumptionReportResponse;
import com.zenhomes.energyconsumption.services.CounterConsumptionService;
import com.zenhomes.energyconsumption.services.parsers.DurationParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Clock;
import java.util.Date;

@RestController
public class CounterConsumptionController {

    private final CounterConsumptionService counterConsumptionService;
    private final DurationParser durationParser;
    private final Clock clock;

    public CounterConsumptionController(CounterConsumptionService counterConsumptionService,
                                        DurationParser durationParser,
                                        Clock clock) {
        this.counterConsumptionService = counterConsumptionService;
        this.durationParser = durationParser;
        this.clock = clock;
    }

    @PostMapping("counter_callback")
    public ResponseEntity<CounterConsumption> createCounterConsumption(@RequestBody @Valid CounterConsumption counterConsumption) {
        final var createdConsumption = counterConsumptionService.createConsumptionRecord(counterConsumption);
        return new ResponseEntity<>(createdConsumption, HttpStatus.CREATED);
    }

    @GetMapping("consumption_report")
    public ResponseEntity<ConsumptionReportResponse> getConsumptionReport(@RequestParam("duration") String durationInText) {
        final var duration = durationParser.parse(durationInText);
        final var reportStartDate = Date.from(clock.instant().minus(duration));

        final var villageConsumptions = counterConsumptionService.calculateVillageConsumptions(reportStartDate);

        return new ResponseEntity<>(new ConsumptionReportResponse(villageConsumptions), HttpStatus.OK);
    }

}