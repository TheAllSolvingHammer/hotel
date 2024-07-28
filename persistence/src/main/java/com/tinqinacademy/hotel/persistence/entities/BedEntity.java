package com.tinqinacademy.hotel.persistence.entities;

import com.tinqinacademy.hotel.persistence.enums.BedTypes;
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
@EqualsAndHashCode()
public class BedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "bed_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Exclude
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private BedTypes type;

    @EqualsAndHashCode.Exclude
    @Column(name = "capacity", nullable = false)
    private Integer capacity;
}
