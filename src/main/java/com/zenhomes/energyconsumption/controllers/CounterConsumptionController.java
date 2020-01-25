package com.zenhomes.energyconsumption.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CounterConsumptionController {

    @PostMapping("counter_callback")
    public ResponseEntity<Void> createCounterConsumption() {
        return ResponseEntity.noContent().build();
    }

}