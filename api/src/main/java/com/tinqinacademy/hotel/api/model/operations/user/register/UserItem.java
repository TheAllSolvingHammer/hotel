package com.tinqinacademy.hotel.api.model.operations.user.register;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
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
public class UserItem {
    @NotBlank(message = "Firstname can not be blank")
    private String firstName;
    @NotBlank(message = "Lastname can not be blank")
    private String lastName;
    @PastOrPresent(message = "Birthday must be in the past")
    private LocalDate dateOfBirth;
    @NotBlank(message = "Phone can not be blank")
    @Size(min=10, max =15, message = "Phone number must be between 10 and 15 digits")
    private String phone;
    @NotBlank(message = "ID can not be blank")
    private String idNumber;
    @Past(message = "Validity must be in the past")
    private LocalDate validity;
    @NotBlank(message = "Authority can not be blank")
    private String authority;
    @PastOrPresent(message = "ID can not be issued in the future")
    private LocalDate issueDate;
}
