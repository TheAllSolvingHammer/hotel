package com.tinqinacademy.hotel.api.model.operations.user.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder(toBuilder = true)
public class BookInput {
    @JsonIgnore
    @NotBlank(message = "Room ID can not be null")
    private UUID roomID;
    @FutureOrPresent(message = "Start date can not be in the past")
    private LocalDate startDate;
    @FutureOrPresent(message = "End date can not be in the past")
    private LocalDate endDate;
    @NotBlank(message = "First name can not be blank")
    private String firstName;
    @NotBlank(message = "Last name can not be blank")
    private String lastName;
    @NotBlank(message = "Phone can not be blank")
    @Size(min=10, max =15, message = "Phone number must be between 10 and 15 digits")
    private String phoneNo;
    @PastOrPresent(message = "Birthday can not be in the future")
    private LocalDate dateOfBirth;
    @Email(message = "Invalid email")
    private String email;
}
