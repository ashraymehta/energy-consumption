package com.zenhomes.energyconsumption.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CounterConsumptionController.class)
class CounterConsumptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldSaveCounterInformation() throws Exception {
        mockMvc.perform(post("/counter_callback")
                .content(readTestResource("counter_callback_request.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private byte[] readTestResource(String resourceLocation) throws IOException {
        return Files.readAllBytes(new ClassPathResource(resourceLocation).getFile().toPath());
    }
}