package com.tinqinacademy.hotel.persistence.entities;

import com.tinqinacademy.hotel.persistence.enums.BathRoom;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class Room implements Entity{
    private UUID id;
    private String roomNumber;
    private Integer floor;
    private BathRoom bathRoom;
    private BigDecimal price;

}
