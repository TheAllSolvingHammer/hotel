package com.tinqinacademy.hotel.api.model.operations.admin.register;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class AdminRegisterOutput implements OperationOutput {
    private List<Data> data;
    private LocalDate startDate;
    private LocalDate endDate;

}
