package com.tinqinacademy.hotel.api.model.operations.admin.partialupdate;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class AdminPartialUpdateOutput implements OperationOutput {
    private String ID;
}
