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

import java.util.*;

import static com.tinqinacademy.hotel.core.specifiers.GuestSpecifications.*;

@Service
@Slf4j
public class AdminRegisterOperationImpl extends BaseProcess implements AdminRegisterOperation {
    private final GuestRepository guestRepository;
    @Autowired
    public AdminRegisterOperationImpl(ConversionService conversionService, ErrorsProcessor errorMapper, Validator validator, GuestRepository guestRepository) {
        super(conversionService, errorMapper, validator);
        this.guestRepository = guestRepository;
    }

    @Override
    public Either<ErrorsProcessor, AdminRegisterOutput> process(@Valid AdminRegisterInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    log.info("Start getGuestReport input: {}", input.toString());

                    Set<GuestEntity> allGuestsInTimePeriod = new HashSet<>(guestRepository.findByStartDateAndEndDate(input.getStartDate(), input.getEndDate()));

                    List<Specification<GuestEntity>> specifications = new ArrayList<>();
                    specifications.add(hasFirstName(input.getFirstName()));
                    specifications.add(hasLastName(input.getLastName()));
                    specifications.add(hasCardNumber(input.getIdNumber()));
                    specifications.add(hasCardIssueAuthority(input.getAuthority()));
                    specifications.add(hasCardValidity(input.getValidity().toString()));
                    specifications.add(hasCardIssueDate(input.getIssueDate().toString()));

                    List<Specification<GuestEntity>> enteredFields = specifications.stream().filter(Objects::nonNull).toList();

                    Specification<GuestEntity> finalSpecification = specificationBuilder(enteredFields);

                    Set<GuestEntity> filteredGuests = new HashSet<>(guestRepository.findAll(finalSpecification));

                    List<GuestEntity> resultGuests = allGuestsInTimePeriod.stream()
                            .filter(filteredGuests::contains)
                            .toList();

                    AdminRegisterOutput adminRegisterOutput = AdminRegisterOutput.builder()
                            .data(resultGuests.stream()
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


                    log.info("End of getGuestReport result: {}", adminRegisterOutput);
                    return adminRegisterOutput;

        }).toEither()
                .mapLeft(InputQueryExceptionCase::handleThrowable));
    }
    private Specification<GuestEntity> specificationBuilder(List<Specification<GuestEntity>> specifications) {
        Specification<GuestEntity> result = specifications.getFirst();
        for (int i = 1; i < specifications.size(); i++) {
            result = result.and(specifications.get(i));
        }
        return result;
    }
}
