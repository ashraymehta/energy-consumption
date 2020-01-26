package com.zenhomes.energyconsumption.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zenhomes.energyconsumption.configuration.ClockTestConfiguration;
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

import java.sql.Date;
import java.time.temporal.ChronoUnit;

import static com.zenhomes.energyconsumption.configuration.ClockTestConfiguration.FIXED_INSTANT;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CounterConsumptionController.class)
@Import({JacksonConfiguration.class, ClockTestConfiguration.class})
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
        final var durationQueryParameter = "24h";
        final var villageConsumptions = VillageConsumptions.of(
                new VillageConsumption("aVillage", 10.0),
                new VillageConsumption("anotherVillage", 14.0)
        );
        final var expectedReportStartDate = Date.from(FIXED_INSTANT.minus(24, ChronoUnit.HOURS));
        final var expectedResponseBody = objectMapper.writeValueAsString(new ConsumptionReportResponse(villageConsumptions));

        when(counterConsumptionService.calculateVillageConsumptions(expectedReportStartDate)).thenReturn(villageConsumptions);

        mockMvc.perform(get("/consumption_report").queryParam("duration", durationQueryParameter))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseBody));

        verify(counterConsumptionService, times(1)).calculateVillageConsumptions(expectedReportStartDate);
    }

    @Nested
    @WebMvcTest(controllers = CounterConsumptionController.class)
    @Import({JacksonConfiguration.class, ClockTestConfiguration.class})
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
        void shouldBehaveNormallyIfAmountIsZeroInTheRequestBody() throws Exception {
            final var counterConsumption = new CounterConsumption("1", 0D);

            mockMvc.perform(post("/counter_callback")
                    .content(objectMapper.writeValueAsBytes(counterConsumption))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());

            verify(counterConsumptionService, times(1)).createConsumptionRecord(counterConsumption);
        }

        @Test
        void shouldReturnBadRequestIfDurationIsNotPresentInConsumptionReportRequest() throws Exception {
            mockMvc.perform(get("/consumption_report"))
                    .andExpect(status().isBadRequest());

            verify(counterConsumptionService, never()).calculateVillageConsumptions(any());
        }

        @Test
        void shouldReturnBadRequestIfDurationIsInvalidInConsumptionReportRequest() throws Exception {
            final var invalidDuration = "something-unexpected";

            mockMvc.perform(get("/consumption_report").queryParam("duration", invalidDuration))
                    .andExpect(status().isBadRequest());

            verify(counterConsumptionService, never()).calculateVillageConsumptions(any());
        }
    }

}