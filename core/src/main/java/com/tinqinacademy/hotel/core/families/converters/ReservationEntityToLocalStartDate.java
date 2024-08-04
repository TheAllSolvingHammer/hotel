package com.tinqinacademy.hotel.core.families.converters;

import com.tinqinacademy.hotel.persistence.entities.ReservationEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ReservationEntityToLocalStartDate implements Converter<List<ReservationEntity>,List<LocalDate>> {
    @Override
    public List<LocalDate> convert(List<ReservationEntity> source) {
        return source.stream()
                .map(ReservationEntity::getStartDate)
                .toList();
    }
}
