package com.tinqinacademy.hotel.core.families.converters;

import com.tinqinacademy.hotel.persistence.entities.BedEntity;
import com.tinqinacademy.hotel.persistence.enums.BedTypes;
import com.tinqinacademy.hotel.persistence.repositorynew.BedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@Component
public class StringInputBedsToBedEntitiesConverter implements Converter<List<String>,List<BedEntity>> {
    private final BedRepository bedRepository;

    @Override
    public List<BedEntity> convert(List<String> source) {

        return source.stream()
                .map(BedTypes::getByCode)
                .filter(bedType -> !bedType.equals(BedTypes.UNKNOWN))
                .map(bedType -> bedRepository.findEntityByType(bedType.name()))
                .toList();
    }
}
