package com.javatemplates.taxicompany.models.carmodel;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class RateConverter implements AttributeConverter<Rate, String> {
    @Override
    public String convertToDatabaseColumn(Rate rate) {
        if(rate == null)
            return null;
        return rate.getRate();
    }

    @Override
    public Rate convertToEntityAttribute(String s) {
        return Stream.of(Rate.values()).filter(v -> v.getRate().equals(s)).
                findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
