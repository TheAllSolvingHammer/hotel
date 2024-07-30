package com.tinqinacademy.hotel.api.model.operations.user.unbook;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class UserUnbookOutput implements OperationOutput {
    private String message;
}
