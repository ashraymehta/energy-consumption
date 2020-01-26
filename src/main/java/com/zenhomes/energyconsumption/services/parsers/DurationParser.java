package com.zenhomes.energyconsumption.services.parsers;

import com.zenhomes.energyconsumption.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class DurationParser {
    private static final String PERIOD_PREFIX_ISO_8601 = "P";
    private static final String TIME_PREFIX_ISO_8601 = "T";

    public Duration parse(String timeDurationInText) {
        try {
            return Duration.parse(PERIOD_PREFIX_ISO_8601 + TIME_PREFIX_ISO_8601 + timeDurationInText);
        } catch (RuntimeException ex) {
            throw new ValidationException(ex);
        }
    }
}