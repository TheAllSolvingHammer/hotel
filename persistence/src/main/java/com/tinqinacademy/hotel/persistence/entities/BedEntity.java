package com.tinqinacademy.hotel.persistence.entities;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class BedEntity implements Entity {
    private UUID id;
    private String type;
    private Integer capacity;
}
