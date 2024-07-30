package com.tinqinacademy.hotel.api.model.operations.user.book;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder(toBuilder = true)
public class UserBookOutput implements OperationOutput {
    private String message;
}
