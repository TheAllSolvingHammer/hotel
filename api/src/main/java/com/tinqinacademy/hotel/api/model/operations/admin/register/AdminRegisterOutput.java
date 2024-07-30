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
    private List<String> data;
    private LocalDate startDate;
    private LocalDate endDate;
    private String firstName;
    private String lastName;
    private String phone;
    private String idNumber;
    private String validity;
    private String authority;
    private LocalDate issueDate;
}
