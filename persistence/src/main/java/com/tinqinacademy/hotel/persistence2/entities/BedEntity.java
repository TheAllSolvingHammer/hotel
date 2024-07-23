package com.tinqinacademy.hotel.persistence2.entities;

import com.tinqinacademy.hotel.persistence2.enums.Bed;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@Entity
public class BedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Enumerated(EnumType.STRING)
    private Bed type;
    private Integer capacity;
}
