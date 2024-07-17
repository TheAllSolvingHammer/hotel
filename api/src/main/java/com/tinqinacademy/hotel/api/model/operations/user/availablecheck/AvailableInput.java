package com.tinqinacademy.hotel.api.model.operations.user.availablecheck;

import com.tinqinacademy.hotel.api.enums.BathRoomType;
import com.tinqinacademy.hotel.api.enums.Beds;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;


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
    @Positive(message = "Bed count can not be negative or zero")
    @Max(value=5, message = "Can not place this many beds in a room, right?")
    private Integer bedCount;
    private Beds bedSize;
    private BathRoomType bathRoomType;
}
