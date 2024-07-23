package com.tinqinacademy.hotel.persistence2.entities;

import com.tinqinacademy.hotel.persistence2.enums.BathRoom;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@Entity
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Integer floor;
    private String roomNumber;
    @Enumerated(EnumType.STRING)
    private BathRoom bathRoom;
    private BigDecimal price;

}
