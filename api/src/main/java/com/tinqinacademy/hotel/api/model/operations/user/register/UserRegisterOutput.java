package com.tinqinacademy.hotel.api.model.operations.user.register;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class UserRegisterOutput implements OperationOutput {
  private String message;
}
