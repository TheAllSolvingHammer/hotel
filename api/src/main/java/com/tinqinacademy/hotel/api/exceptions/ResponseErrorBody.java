package com.tinqinacademy.hotel.api.exceptions;

import lombok.*;

import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class ResponseErrorBody
{
   private List<ErrorsProcessor> errors;
}
