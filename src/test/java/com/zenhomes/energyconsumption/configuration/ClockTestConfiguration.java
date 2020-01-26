package com.zenhomes.energyconsumption.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@TestConfiguration
public class ClockTestConfiguration {

    public static final Instant FIXED_INSTANT = Instant.parse("2020-01-01T00:00:00.00Z");

    @Bean
    public Clock clock() {
        return Clock.fixed(FIXED_INSTANT, ZoneId.systemDefault());
    }
}
