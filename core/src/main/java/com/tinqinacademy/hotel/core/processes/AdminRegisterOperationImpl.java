package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.hotel.api.model.operations.admin.register.AdminRegisterInput;
import com.tinqinacademy.hotel.api.model.operations.admin.register.AdminRegisterOperation;
import com.tinqinacademy.hotel.api.model.operations.admin.register.AdminRegisterOutput;
import com.tinqinacademy.hotel.api.model.operations.admin.register.Data;
import com.tinqinacademy.hotel.core.families.casehandlers.InputQueryExceptionCase;
import com.tinqinacademy.hotel.core.specifiers.SpecificationsUtil;
import com.tinqinacademy.hotel.persistence.entities.GuestEntity;
import com.tinqinacademy.hotel.persistence.repositorynew.GuestRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
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
    public AdminRegisterOperationImpl(ConversionService conversionService,ErrorsProcessor errorMapper, Validator validator, GuestRepository guestRepository) {
        super(conversionService, errorMapper, validator);
        this.guestRepository = guestRepository;
    }

    @Override
    public Either<ErrorsProcessor, AdminRegisterOutput> process(AdminRegisterInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    log.info("Start getGuestReport input: {}", input.toString());

                    List<Specification<GuestEntity>> predicates = getSpecifications(input);

                    Specification<GuestEntity> specification = SpecificationsUtil.combineSpecifications(predicates);

                    Set<GuestEntity> specifiedGuests = new HashSet<>(guestRepository.findAll(specification));

                    List<GuestEntity> allGuests = guestRepository.findByStartDateAndEndDate(
                            input.getStartDate(), input.getEndDate(), input.getRoom()
                    );


                    List<GuestEntity> filteredGuests = allGuests.stream()
                            .filter(specifiedGuests::contains)
                            .toList();

                    List<Data> guestInfo = filteredGuests.stream()
                            .map(guest -> conversionService.convert(guest, Data.class))
                            .toList();

                    AdminRegisterOutput output = outputBuilder(guestInfo);

                    log.info("End getRegisterInfo output: {}", output);
                    return output;

        }).toEither()
                .mapLeft(InputQueryExceptionCase::handleThrowable));
    }
    private AdminRegisterOutput outputBuilder(List<Data> guestInfo) {
        return AdminRegisterOutput.builder()
                .data(guestInfo)
                .build();
    }

    private  List<Specification<GuestEntity>> getSpecifications(AdminRegisterInput input) {
        return new ArrayList<>() {{
            add(guestHasFirstName(input.getFirstName()));
            add(guestHasLastName(input.getLastName()));
            add(guestHasPhoneNumber(input.getPhone()));
            add(guestHasIdCardNumber(input.getIdNumber()));
            add(guestHasIdCardValidity(String.valueOf(input.getValidity())));
            add(guestHasIdCardIssueDate(String.valueOf(input.getIssueDate())));
            add(guestHasIdCardIssueAuthority(input.getAuthority()));
        }};
    }
}
