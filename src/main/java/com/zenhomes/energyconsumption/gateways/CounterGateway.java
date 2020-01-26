package com.zenhomes.energyconsumption.gateways;

import com.zenhomes.energyconsumption.configuration.CounterServiceConfiguration;
import com.zenhomes.energyconsumption.models.dto.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static java.text.MessageFormat.format;

@Component
public class CounterGateway {

    private final RestTemplate restTemplate;
    private final CounterServiceConfiguration counterServiceConfiguration;
    private final Logger logger = LoggerFactory.getLogger(CounterGateway.class);

    public CounterGateway(RestTemplateBuilder restTemplateBuilder,
                          CounterServiceConfiguration counterServiceConfiguration) {
        this.restTemplate = restTemplateBuilder.build();
        this.counterServiceConfiguration = counterServiceConfiguration;
    }

    public Counter getCounter(String counterId) {
        try {
            final var uri = UriComponentsBuilder.fromHttpUrl(counterServiceConfiguration.getBaseUrl())
                    .path(GetCounterApi.PATH)
                    .queryParam(GetCounterApi.QUERY_PARAM_COUNTER_ID, counterId)
                    .build().toUri();
            return restTemplate.getForObject(uri, Counter.class);
        } catch (Exception ex) {
            logger.error(format("Could not find counter information for id [{0}]", counterId));
            throw ex;
        }
    }

    interface GetCounterApi {
        String PATH = "counter";
        String QUERY_PARAM_COUNTER_ID = "id";
    }
}