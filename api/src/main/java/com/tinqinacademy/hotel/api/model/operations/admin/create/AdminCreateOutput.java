package com.tinqinacademy.hotel.api.model.operations.admin.create;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class AdminCreateOutput implements OperationOutput {
    private UUID ID;
    private String roomNumber;
}
