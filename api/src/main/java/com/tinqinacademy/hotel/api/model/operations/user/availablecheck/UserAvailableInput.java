package com.tinqinacademy.hotel.api.model.operations.user.availablecheck;

import com.tinqinacademy.hotel.api.base.OperationInput;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class UserAvailableInput implements OperationInput {
    @FutureOrPresent(message = "Start date can not be in the past")
    private LocalDate startDate;
    @FutureOrPresent(message = "End date can not be in the past")
    private LocalDate endDate;
    private String bathRoom;
    @NotBlank(message = "bed is empty")
    private String bed;
}
