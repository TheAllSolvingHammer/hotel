package com.tinqinacademy.hotel.api.model.operations.admin.create;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class AdminCreateInput {
    @NotBlank
    private String roomNumber;

    private List<String> bedType;
    private String bathRoom;
    @Positive(message = "Floor can not be negative or zero")
    @Max(value=20, message = "Floor can not be more than 20")
    private Integer floor;
    @PositiveOrZero(message = "price can not be negative")
    private BigDecimal price;
}
