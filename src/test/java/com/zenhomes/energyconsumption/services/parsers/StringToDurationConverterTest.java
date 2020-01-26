package com.zenhomes.energyconsumption.services.parsers;

import com.zenhomes.energyconsumption.exceptions.ValidationException;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringToDurationConverterTest {

    private final StringToDurationConverter stringToDurationConverter = new StringToDurationConverter();

    @Test
    void shouldParseDurationInHours() {
        assertThat(stringToDurationConverter.convert("24h"), is(equalTo(Duration.ofHours(24))));
        assertThat(stringToDurationConverter.convert("24H"), is(equalTo(Duration.ofHours(24))));
        assertThat(stringToDurationConverter.convert("8H"), is(equalTo(Duration.ofHours(8))));
    }

    @Test
    void shouldParseDurationInMinutes() {
        assertThat(stringToDurationConverter.convert("1m"), is(equalTo(Duration.ofMinutes(1))));
        assertThat(stringToDurationConverter.convert("2M"), is(equalTo(Duration.ofMinutes(2))));
    }

    @Test
    void shouldParseDurationInHoursAndMinutes() {
        assertThat(stringToDurationConverter.convert("2H30M"), is(equalTo(Duration.ofHours(2).plus(Duration.ofMinutes(30)))));
        assertThat(stringToDurationConverter.convert("2h30m"), is(equalTo(Duration.ofHours(2).plus(Duration.ofMinutes(30)))));
    }

    @Test
    void shouldThrowValidationExceptionIfTheFormatIsIncorrect() {
        assertThrows(ValidationException.class, () -> stringToDurationConverter.convert(""));
        assertThrows(ValidationException.class, () -> stringToDurationConverter.convert("something-totally-unrelated"));
    }
}