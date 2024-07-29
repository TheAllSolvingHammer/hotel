package com.tinqinacademy.hotel.api.model.operations.admin.update;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class AdminUpdateOutput {
    private UUID ID;
    private String roomNumber;
}
