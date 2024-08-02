package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.model.operations.user.unbook.UserUnbookInput;
import com.tinqinacademy.hotel.api.model.operations.user.unbook.UserUnbookOperation;
import com.tinqinacademy.hotel.api.model.operations.user.unbook.UserUnbookOutput;
import com.tinqinacademy.hotel.core.exceptionfamilies.InputQueryExceptionCase;
import com.tinqinacademy.hotel.persistence.repositorynew.ReservationRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserUnbookOperationImpl extends BaseOperation<UserUnbookOutput,UserUnbookInput> implements UserUnbookOperation {
    private final ReservationRepository reservationRepository;
    @Override
    public Either<ErrorsProcessor, UserUnbookOutput> process(UserUnbookInput input) {
        return Try.of(()->{
            log.info("Start unbook room: {}", input);
            reservationRepository.deleteById(input.getBookId());
            UserUnbookOutput userUnbookOutput = UserUnbookOutput.builder()
                    .message("Successfully unbooked a room")
                    .build();
            log.info("End unbook room: {}", userUnbookOutput);
            return userUnbookOutput;
        })
                .toEither()
                .mapLeft(InputQueryExceptionCase::handleThrowable);
    }
}
