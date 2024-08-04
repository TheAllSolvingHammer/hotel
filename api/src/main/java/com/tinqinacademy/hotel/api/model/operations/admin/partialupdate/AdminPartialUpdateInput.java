package com.tinqinacademy.hotel.api.model.operations.admin.partialupdate;

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
@NoArgsConstructor()
@AllArgsConstructor()
@Builder(toBuilder = true)
public class AdminPartialUpdateInput implements OperationInput {
    @JsonIgnore
    private UUID roomID;
    private List<String> bedTypes;
    private String bathRoom;
    @Positive(message = "Floor can not be negative or zero")
    @PositiveOrZero(message="Price can not be negative")
    private BigDecimal price;
    private String roomNumber;
}
