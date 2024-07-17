package com.tinqinacademy.hotel.api.model.operations.user.register;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class RegisterInput {

    private List<String> data;
    @FutureOrPresent(message = "Start date can not be in the past")
    private LocalDate startDate;
    @FutureOrPresent(message = "End date can not be in the past")
    private LocalDate endDate;
    @NotBlank(message = "Firstname can not be blank")
    private String firstName;
    @NotBlank(message = "Lastname can not be blank")
    private String lastName;
    @NotBlank(message = "Phone can not be blank")
    @Size(min=10, max =15, message = "Phone number must be between 10 and 15 digits")
    private String phone;
    @NotBlank(message = "ID can not be blank")
    private String idNumber;
    @NotBlank(message = "Validity can not be blank")
    private String validity;
    @NotBlank(message = "Authority can not be blank")
    private String authority;
    @PastOrPresent(message = "ID can not be issued in the future")
    private LocalDate issueDate;
}
