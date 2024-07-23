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
@Table(name="beds")
public class BedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bed_id", updatable = false, nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Bed type;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;
}
