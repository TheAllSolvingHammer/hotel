package com.tinqinacademy.hotel.api.model.previous.room;

import com.tinqinacademy.hotel.api.enums.BathRoom;
import com.tinqinacademy.hotel.api.enums.Bed;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class RoomInput {
    private String id;
    private int bedCount;
    private Bed bed;
    private int floor;
    private BigDecimal price;
    private BathRoom roomType;
}
