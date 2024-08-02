package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.base.OperationInput;
import com.tinqinacademy.hotel.api.base.OperationOutput;
import com.tinqinacademy.hotel.api.base.OperationProcessor;
import com.tinqinacademy.hotel.api.exceptions.InputException;
import io.vavr.API;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;
@NoArgsConstructor()
@Slf4j
@Service
public abstract class BaseOperation<T extends OperationOutput,E extends OperationInput>  implements OperationProcessor<T,E> {

    public ResponseEntity<?> handleOperation(E input) {
        Either<ErrorsProcessor, T> result = process(input);
        return result.fold(
                error -> ResponseEntity.status(error.getHttpStatus()).body(error),
                success-> ResponseEntity.status(HttpStatus.OK.value()).body(result)
        );

    }


}
