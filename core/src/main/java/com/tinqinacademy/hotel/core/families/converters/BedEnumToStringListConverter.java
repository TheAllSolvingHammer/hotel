package com.tinqinacademy.hotel.core.families.converters;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.core.convert.converter.Converter;
import com.tinqinacademy.hotel.api.enums.Bed;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class BedEnumToStringListConverter implements Converter<Bed[], List<String>> {

    @Override
    public List<String> convert(Bed[] source) {
        return Arrays.stream(source)
                .map(Bed::toString)
                .toList();
    }
}


