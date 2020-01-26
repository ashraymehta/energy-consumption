package com.zenhomes.energyconsumption.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;

public class ResourceUtil {
    public static byte[] readTestResource(String resourceLocation) throws IOException {
        return Files.readAllBytes(new ClassPathResource(resourceLocation).getFile().toPath());
    }
}