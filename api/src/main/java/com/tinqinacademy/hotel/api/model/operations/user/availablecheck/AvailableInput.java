package com.tinqinacademy.hotel.api.model.operations.user.availablecheck;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tinqinacademy.hotel.api.enums.BathRoom;
import com.tinqinacademy.hotel.api.enums.Bed;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class AvailableInput {
    @FutureOrPresent(message = "Start date can not be in the past")
    private LocalDate startDate;
    @FutureOrPresent(message = "End date can not be in the past")
    private LocalDate endDate;
    private BathRoom bathRoom;
    @NotBlank(message = "bed is empty")
    private String bed;
}
