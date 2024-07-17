package com.tinqinacademy.hotel.api.model.operations.admin.update;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.api.enums.BathRoomType;
import com.tinqinacademy.hotel.api.enums.Beds;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AdminUpdateInput {
    @Positive(message = "Bed count can not be negative or zero")
    @Max(value=5, message = "Can not place this many beds in a room, right?")
    private Integer bedCount;
    private Beds bedSize;
    private BathRoomType bathRoomType;
    @Positive(message = "Floor can not be negative or zero")
    @Max(value=20, message = "The building does not have this many floors")
    private Integer floor;
    @JsonIgnore
    private String roomID;
    @PositiveOrZero(message = "Price can not be negative number")
    private BigDecimal price;
}
