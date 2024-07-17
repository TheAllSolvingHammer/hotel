package com.tinqinacademy.hotel.api.model.operations.admin.partialupdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.api.enums.BathRoomType;
import com.tinqinacademy.hotel.api.enums.Beds;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder(toBuilder = true)
public class AdminPartialUpdateInput {
    @Positive(message = "Bed count can not be negative or zero")
    @Max(value=5, message = "Can not place this many beds in a room, right?")
    private Integer bedCount;
    private Beds bedSize;
    private BathRoomType bathRoomType;
    @Positive(message = "Floor can not be negative or zero")
    @Max(value=20, message = "Floor can not be more than 20")
    private Integer floor;
    @JsonIgnore
    private String roomID;
    @PositiveOrZero(message="Price can not be negative")
    private BigDecimal price;
}
