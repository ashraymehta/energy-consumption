package com.zenhomes.electricitycounters;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void shouldBootUpApplication() {
        assertThat(context, is(notNullValue()));
    }
}