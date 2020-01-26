package com.zenhomes.energyconsumption.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zenhomes.energyconsumption.configuration.JacksonConfiguration;
import com.zenhomes.energyconsumption.models.CounterConsumption;
import com.zenhomes.energyconsumption.models.dto.ConsumptionReportResponse;
import com.zenhomes.energyconsumption.models.dto.VillageConsumption;
import com.zenhomes.energyconsumption.models.dto.VillageConsumptions;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
        when(counterConsumptionService.createConsumptionRecord(counterConsumption)).thenReturn(counterConsumption);

        mockMvc.perform(post("/counter_callback")
                .content(objectMapper.writeValueAsBytes(counterConsumption))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(counterConsumption)));

        verify(counterConsumptionService, times(1)).createConsumptionRecord(counterConsumption);
    }

    @Test
    void shouldProvideConsumptionReport() throws Exception {
        final var villageConsumptions = VillageConsumptions.of(
                new VillageConsumption("aVillage", 10.0),
                new VillageConsumption("anotherVillage", 14.0)
        );
        when(counterConsumptionService.calculateVillageConsumptions(any())).thenReturn(villageConsumptions);
        final var expectedResponseBody = objectMapper.writeValueAsString(new ConsumptionReportResponse(villageConsumptions));

        mockMvc.perform(get("/consumption_report"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseBody));

        verify(counterConsumptionService, times(1)).calculateVillageConsumptions(any());
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
                    .andExpect(status().isCreated());

            verify(counterConsumptionService, times(1)).createConsumptionRecord(counterConsumption);
        }
    }

}