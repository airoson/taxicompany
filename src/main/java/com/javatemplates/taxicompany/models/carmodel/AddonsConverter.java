package com.javatemplates.taxicompany.models.carmodel;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Converter(autoApply = true)
@Slf4j
public class AddonsConverter implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(List<String> strings) {
        if(strings != null) {
            String s =  strings.stream().reduce((a, b) -> a + "|" + b).orElse(null);
            return s;
        }
        else{
            return null;
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        if(s != null) {
            return Arrays.asList(s.split("\\|"));
        }
        else{
            return null;
        }
    }
}
