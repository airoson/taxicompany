package com.javatemplates.taxicompany.models.carmodel;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class GearboxConverter implements AttributeConverter<Gearbox, String> {
    @Override
    public String convertToDatabaseColumn(Gearbox gearbox) {
        if(gearbox == null)
            return null;
        return gearbox.getType();
    }

    @Override
    public Gearbox convertToEntityAttribute(String s) {
        return Stream.of(Gearbox.values()).filter(v -> v.getType().equals(s))
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
