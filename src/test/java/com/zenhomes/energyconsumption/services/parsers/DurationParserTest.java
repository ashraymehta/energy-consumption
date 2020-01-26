package com.zenhomes.energyconsumption.services.parsers;

import com.zenhomes.energyconsumption.exceptions.ValidationException;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DurationParserTest {

    private final DurationParser durationParser = new DurationParser();

    @Test
    void shouldParseDurationInHours() {
        assertThat(durationParser.parse("24h"), is(equalTo(Duration.ofHours(24))));
        assertThat(durationParser.parse("24H"), is(equalTo(Duration.ofHours(24))));
        assertThat(durationParser.parse("8H"), is(equalTo(Duration.ofHours(8))));
    }

    @Test
    void shouldParseDurationInMinutes() {
        assertThat(durationParser.parse("1m"), is(equalTo(Duration.ofMinutes(1))));
        assertThat(durationParser.parse("2M"), is(equalTo(Duration.ofMinutes(2))));
    }

    @Test
    void shouldParseDurationInHoursAndMinutes() {
        assertThat(durationParser.parse("2H30M"), is(equalTo(Duration.ofHours(2).plus(Duration.ofMinutes(30)))));
        assertThat(durationParser.parse("2h30m"), is(equalTo(Duration.ofHours(2).plus(Duration.ofMinutes(30)))));
    }

    @Test
    void shouldThrowValidationExceptionIfTheFormatIsIncorrect() {
        assertThrows(ValidationException.class, () -> durationParser.parse(""));
        assertThrows(ValidationException.class, () -> durationParser.parse("something-totally-unrelated"));
    }
}