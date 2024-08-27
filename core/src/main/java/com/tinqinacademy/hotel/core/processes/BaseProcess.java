package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.base.OperationInput;
import com.tinqinacademy.hotel.api.exceptions.ErrorsProcessor;
import io.vavr.control.Either;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Slf4j
public class BaseProcess {
    protected final ConversionService conversionService;
    private final ErrorsProcessor errorMapper;
    private final Validator validator;
    @Autowired
    public BaseProcess(ConversionService conversionService, ErrorsProcessor errorMapper, Validator validator) {
        this.conversionService = conversionService;
        this.errorMapper = errorMapper;
        this.validator = validator;
    }

    public Either<ErrorsProcessor, OperationInput> validateInput(OperationInput input) {
        Set<ConstraintViolation<OperationInput>> violations = validator.validate(input);
        if (!violations.isEmpty()) {
            List<String> errorMessages = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());
            ErrorsProcessor errorsProcessor = ErrorsProcessor.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .message(String.join(", ", errorMessages))
                    .build();

            return Either.left(errorsProcessor);
        }

        return Either.right(input);
    }

}
