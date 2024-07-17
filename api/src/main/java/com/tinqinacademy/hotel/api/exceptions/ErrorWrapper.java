package com.tinqinacademy.hotel.api.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class ErrorWrapper {
    private String message;
    private HttpStatus status;
    private Integer statusCode;
    private LocalDate timestamp;
}
