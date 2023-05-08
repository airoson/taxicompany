package com.javatemplates.taxicompany.models;

import jakarta.persistence.AttributeConverter;

import java.nio.channels.IllegalSelectorException;
import java.util.Arrays;
import java.util.List;

public class UserRolesConverter implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(List<String> strings) {
        return strings.stream().reduce((a, b) -> a + "\n" + b).orElseThrow(IllegalSelectorException::new);
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        return Arrays.asList(s.split("\n"));
    }
}
