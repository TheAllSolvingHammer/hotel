package com.tinqinacademy.hotel.api.model.operations.admin.register;

import com.tinqinacademy.hotel.api.base.OperationInput;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class AdminRegisterInput implements OperationInput {

    private LocalDate startDate;

    private LocalDate endDate;
    @NotBlank(message = "First name can not be blank")
    private String firstName;
    @NotBlank(message = "Last name can not be blank")
    private String lastName;
    @NotBlank(message = "Phone can not be blank")
    @Size(min=10, max =15, message = "Phone number must be between 10 and 15 digits")
    private String phone;
    @NotBlank(message = "Room ID can not be blank")
    private String idNumber;
    private LocalDate validity;
    @NotBlank(message = "Authority can not be blank")
    private String authority;
    @PastOrPresent(message = "Issue date can not be in the future")
    private LocalDate issueDate;
    private String roomID;
}
