package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.hotel.api.model.operations.user.unbook.UserUnbookInput;
import com.tinqinacademy.hotel.api.model.operations.user.unbook.UserUnbookOperation;
import com.tinqinacademy.hotel.api.model.operations.user.unbook.UserUnbookOutput;
import com.tinqinacademy.hotel.core.families.casehandlers.InputQueryExceptionCase;
import com.tinqinacademy.hotel.persistence.repositorynew.ReservationRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserUnbookOperationImpl extends BaseProcess implements UserUnbookOperation {
    private final ReservationRepository reservationRepository;

    @Autowired
    public UserUnbookOperationImpl(ConversionService conversionService, ErrorsProcessor errorMapper, Validator validator, ReservationRepository reservationRepository) {
        super(conversionService, errorMapper, validator);
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Either<ErrorsProcessor, UserUnbookOutput> process(UserUnbookInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
            log.info("Start unbook room: {}", input);
            reservationRepository.deleteById(input.getBookId());
            UserUnbookOutput userUnbookOutput = UserUnbookOutput.builder()
                    .message("Successfully unbooked a room")
                    .build();
            log.info("End unbook room: {}", userUnbookOutput);
            return userUnbookOutput;
        })
                .toEither()
                .mapLeft(InputQueryExceptionCase::handleThrowable));
    }
}
