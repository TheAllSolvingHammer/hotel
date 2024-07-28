package com.tinqinacademy.hotel.api.model.operations.user.displayroom;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class DisplayRoomInput {
    @NotBlank(message = "Room ID can not be blank")
    private UUID roomID;
}
