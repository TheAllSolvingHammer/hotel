package com.tinqinacademy.hotel.api.model.operations.admin.partialupdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.api.base.OperationInput;
import com.tinqinacademy.hotel.api.enums.BathRoom;
import com.tinqinacademy.hotel.api.enums.Bed;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder(toBuilder = true)
public class AdminPartialUpdateInput implements OperationInput {
    @Positive(message = "Bed count can not be negative or zero")
    @Max(value=5, message = "Can not place this many beds in a room, right?")
    private Integer bedCount;
    private Bed bedSize;
    private BathRoom bathRoom;
    @Positive(message = "Floor can not be negative or zero")
    @Max(value=20, message = "Floor can not be more than 20")
    private Integer floor;
    @JsonIgnore
    private String roomID;
    @PositiveOrZero(message="Price can not be negative")
    private BigDecimal price;
}
