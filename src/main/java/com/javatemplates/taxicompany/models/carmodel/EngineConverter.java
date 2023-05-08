package com.javatemplates.taxicompany.models.carmodel;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

@Slf4j
@Converter(autoApply = true)
public class EngineConverter implements AttributeConverter<Engine, String> {
    @Override
    public String convertToDatabaseColumn(Engine engine) {
        if(engine == null) {
            log.error("Trying to pass null in convertToDatabaseColumn");
            return null;
        }
        return engine.getType();
    }

    @Override
    public Engine convertToEntityAttribute(String s) {
        return Stream.of(Engine.values()).filter(e -> e.getType().equals(s)).
                findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
