package com.tinqinacademy.hotel.api.exceptions;

import org.springframework.web.bind.MethodArgumentNotValidException;

public interface ExceptionService {
    ResponseErrorBody handle(MethodArgumentNotValidException ex);
    ResponseErrorBody handle(InputException ex);
    ResponseErrorBody handle(Exception ex);
}
