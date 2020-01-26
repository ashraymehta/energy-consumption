package com.zenhomes.energyconsumption.gateways;

import com.zenhomes.energyconsumption.configuration.CounterServiceConfiguration;
import com.zenhomes.energyconsumption.configuration.JacksonConfiguration;
import com.zenhomes.energyconsumption.models.dto.Counter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.io.IOException;

import static com.zenhomes.energyconsumption.utils.ResourceUtil.readTestResource;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@Import(JacksonConfiguration.class)
@RestClientTest(CounterGateway.class)
class CounterGatewayTest {

    private static final String BASE_URL = "https://localhost:3000/";

    @Autowired
    private CounterGateway counterGateway;

    @Autowired
    private MockRestServiceServer server;

    @MockBean
    private CounterServiceConfiguration counterServiceConfiguration;

    @BeforeEach
    void setUp() {
        when(counterServiceConfiguration.getBaseUrl()).thenReturn(BASE_URL);
    }

    @Test
    void shouldGetCounterByCounterId() throws IOException {
        final var counterId = "aCounterId";
        server.expect(requestTo(BASE_URL + "counter?id=" + counterId))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(readTestResource("counter_get_request.json")));

        final var counter = counterGateway.getCounter(counterId);

        assertThat(counter, is(new Counter("1", "Villarriba")));
    }
}