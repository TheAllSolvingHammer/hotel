package com.tinqinacademy.hotel.api.model.operations.admin.create;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class AdminCreateOutput {
    private UUID ID;
    private String roomNumber;
}
