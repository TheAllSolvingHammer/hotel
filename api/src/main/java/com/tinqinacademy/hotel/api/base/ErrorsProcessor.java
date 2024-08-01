package com.tinqinacademy.hotel.api.base;

import lombok.*;
import org.springframework.http.HttpStatus;
@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class ErrorsProcessor {
    private HttpStatus httpStatus;
    private Integer statusCode;
    private String message;
}
