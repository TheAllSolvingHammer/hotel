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
@Table(name="rooms")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "room_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "floor", nullable = false)
    private Integer floor;

    @Column(name = "room_number", nullable = false, length = 14)
    private String roomNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "bath_room", nullable = false)
    private BathRoom bathRoom;

    @Column(name = "price", nullable = false)
    private BigDecimal price;
}
