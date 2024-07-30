package com.tinqinacademy.hotel.api.model.operations.admin.delete;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class AdminDeleteOutput implements OperationOutput {
    private String message;
}
