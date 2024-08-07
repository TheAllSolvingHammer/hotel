package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.hotel.api.model.operations.admin.register.AdminRegisterInput;
import com.tinqinacademy.hotel.api.model.operations.admin.register.AdminRegisterOperation;
import com.tinqinacademy.hotel.api.model.operations.admin.register.AdminRegisterOutput;
import com.tinqinacademy.hotel.core.families.casehandlers.InputQueryExceptionCase;
import com.tinqinacademy.hotel.core.specifiers.GuestSpecifications;
import com.tinqinacademy.hotel.persistence.entities.GuestEntity;
import com.tinqinacademy.hotel.persistence.repositorynew.GuestRepository;
import com.tinqinacademy.hotel.persistence.repositorynew.ReservationRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AdminRegisterOperationImpl extends BaseProcess implements AdminRegisterOperation {
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;
    @Autowired
    public AdminRegisterOperationImpl(ConversionService conversionService, ErrorsProcessor errorMapper, Validator validator, GuestRepository guestRepository, ReservationRepository reservationRepository) {
        super(conversionService, errorMapper, validator);
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Either<ErrorsProcessor, AdminRegisterOutput> process(@Valid AdminRegisterInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    log.info("Start admin info: {}", input);

                    Specification<GuestEntity> specification = Specification.where(GuestSpecifications.hasRoomId(input.getRoomID()))
                            .and(GuestSpecifications.betweenDates(input.getStartDate(), input.getEndDate()))
                            .and(GuestSpecifications.hasFirstName(input.getFirstName()))
                            .and(GuestSpecifications.hasLastName(input.getLastName()))
                            .and(GuestSpecifications.hasPhone(input.getPhone()))
                            .and(GuestSpecifications.hasIdNumber(input.getIdNumber()));

                    log.info("Test{}",specification);

                    List<GuestEntity> guests = guestRepository.findAll(specification);

                    AdminRegisterOutput adminRegisterOutput = AdminRegisterOutput.builder()
                            .data(guests.stream()
                                    .map(guest -> String.format("Guest: %s %s, Phone: %s, ID: %s", guest.getFirstName(), guest.getLastName(), guest.getPhoneNumber(), guest.getIdCardNumber()))
                                    .toList())
                            .startDate(input.getStartDate())
                            .endDate(input.getEndDate())
                            .firstName(input.getFirstName())
                            .lastName(input.getLastName())
                            .phone(input.getPhone())
                            .idNumber(input.getIdNumber())
                            .validity(input.getValidity())
                            .authority(input.getAuthority())
                            .issueDate(input.getIssueDate())
                            .roomID(input.getRoomID())
                            .build();

                    log.info("End admin info: {}", adminRegisterOutput);
                    return adminRegisterOutput;



        }).toEither()
                .mapLeft(InputQueryExceptionCase::handleThrowable));
    }
}
