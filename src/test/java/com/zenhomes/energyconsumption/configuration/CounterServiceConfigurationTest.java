package com.zenhomes.energyconsumption.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SpringBootTest
class CounterServiceConfigurationTest {

    @Autowired
    private CounterServiceConfiguration counterServiceConfiguration;

    @Test
    void shouldGetCounterServiceBaseUrl() {
        assertThat(counterServiceConfiguration.getBaseUrl(), is("http://localhost:12001/"));
    }
}