package com.zenhomes.energyconsumption.controllers;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zenhomes.energyconsumption.models.CounterConsumption;
import com.zenhomes.energyconsumption.services.CounterConsumptionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        final var counterConsumption = new CounterConsumption("1", 1000);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        mockMvc.perform(post("/counter_callback")
                .content(objectMapper.writeValueAsBytes(counterConsumption))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(counterConsumptionService).createConsumptionRecord(counterConsumption);
    }
}