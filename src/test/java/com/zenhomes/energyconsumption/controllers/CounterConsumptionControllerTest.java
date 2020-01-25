package com.zenhomes.energyconsumption.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zenhomes.energyconsumption.configuration.JacksonConfiguration;
import com.zenhomes.energyconsumption.models.CounterConsumption;
import com.zenhomes.energyconsumption.services.CounterConsumptionService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(JacksonConfiguration.class)
@WebMvcTest(CounterConsumptionController.class)
class CounterConsumptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CounterConsumptionService counterConsumptionService;

    @Test
    void shouldSaveCounterInformation() throws Exception {
        final var counterConsumption = new CounterConsumption("1", 1000D);

        mockMvc.perform(post("/counter_callback")
                .content(objectMapper.writeValueAsBytes(counterConsumption))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(counterConsumptionService, times(1)).createConsumptionRecord(counterConsumption);
    }

    @Nested
    @Import(JacksonConfiguration.class)
    @WebMvcTest(controllers = CounterConsumptionController.class)
    class CounterConsumptionControllerValidationsTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private CounterConsumptionService counterConsumptionService;

        @Test
        void shouldReturnBadRequestIfCounterIdIsMissingFromRequestBody() throws Exception {
            mockMvc.perform(post("/counter_callback")
                    .content("{\"amount\": 1000}}")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            verify(counterConsumptionService, never()).createConsumptionRecord(any());
        }

        @Test
        void shouldReturnBadRequestIfCounterIdIsEmptyInTheRequestBody() throws Exception {
            final var counterConsumption = new CounterConsumption("", -200D);

            mockMvc.perform(post("/counter_callback")
                    .content(objectMapper.writeValueAsBytes(counterConsumption))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            verify(counterConsumptionService, never()).createConsumptionRecord(any());
        }

        @Test
        void shouldReturnBadRequestIfAmountIsMissingFromRequestBody() throws Exception {
            mockMvc.perform(post("/counter_callback")
                    .content("{\"counterId\": \"1\"}}")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            verify(counterConsumptionService, never()).createConsumptionRecord(any());
        }

        @Test
        void shouldReturnBadRequestIfAmountIsNegativeInTheRequestBody() throws Exception {
            final var counterConsumption = new CounterConsumption("1", -200D);

            mockMvc.perform(post("/counter_callback")
                    .content(objectMapper.writeValueAsBytes(counterConsumption))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            verify(counterConsumptionService, never()).createConsumptionRecord(counterConsumption);
        }

        @Test
        void shouldReturnBehaveNormallyIfAmountIsZeroInTheRequestBody() throws Exception {
            final var counterConsumption = new CounterConsumption("1", 0D);

            mockMvc.perform(post("/counter_callback")
                    .content(objectMapper.writeValueAsBytes(counterConsumption))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());

            verify(counterConsumptionService, times(1)).createConsumptionRecord(counterConsumption);
        }
    }

}