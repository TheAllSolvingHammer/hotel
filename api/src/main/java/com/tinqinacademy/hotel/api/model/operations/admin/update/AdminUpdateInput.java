package com.tinqinacademy.hotel.api.model.operations.admin.update;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.api.base.OperationInput;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AdminUpdateInput implements OperationInput{
    @JsonIgnore
    private UUID roomID;
    private String roomNumber;
    private List<String> bedSize;
    private String bathRoom;
    @Positive(message = "Floor can not be negative or zero")
    @Max(value=20, message = "The building does not have this many floors")
    private Integer floor;
    @PositiveOrZero(message = "Price can not be negative number")
    private BigDecimal price;
}
