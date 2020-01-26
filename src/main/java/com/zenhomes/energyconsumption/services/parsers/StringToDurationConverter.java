package com.zenhomes.energyconsumption.services.parsers;

import com.zenhomes.energyconsumption.exceptions.ValidationException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class StringToDurationConverter implements Converter<String, Duration> {
    private static final String PERIOD_PREFIX_ISO_8601 = "P";
    private static final String TIME_PREFIX_ISO_8601 = "T";

    @Override
    public Duration convert(String source) {
        try {
            return Duration.parse(PERIOD_PREFIX_ISO_8601 + TIME_PREFIX_ISO_8601 + source);
        } catch (RuntimeException ex) {
            throw new ValidationException(ex);
        }
    }
}