package com.tinqinacademy.hotel.api.model.previous.room;

import com.tinqinacademy.hotel.api.enums.BathRoomType;
import com.tinqinacademy.hotel.api.enums.Beds;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class RoomOutput {
    private String id;
    private int bedCount;
    private Beds bed;
    private int floor;
    private BigDecimal price;
    private BathRoomType roomType;
}
