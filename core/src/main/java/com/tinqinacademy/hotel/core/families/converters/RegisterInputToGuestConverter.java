package com.tinqinacademy.hotel.core.families.converters;

import com.tinqinacademy.hotel.api.model.operations.user.register.UserRegisterInput;
import com.tinqinacademy.hotel.persistence.entities.GuestEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class RegisterInputToGuestConverter implements Converter<UserRegisterInput, List<GuestEntity>> {
    @Override
    public List<GuestEntity> convert(UserRegisterInput source) {
        return source.getUsers().stream().map(e -> GuestEntity.builder()
                .authority(e.getAuthority())
                .birthDate(e.getDateOfBirth())
                .firstName(e.getFirstName())
                .lastName(e.getLastName())
                .validity(e.getValidity())
                .issueDate(e.getIssueDate())
                .idCardNumber(e.getIdNumber())
                .phoneNumber(e.getPhone())
                .build()).toList();
    }
}
