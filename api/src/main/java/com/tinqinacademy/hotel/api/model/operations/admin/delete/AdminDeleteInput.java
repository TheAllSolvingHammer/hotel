package com.tinqinacademy.hotel.api.model.operations.admin.delete;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class AdminDeleteInput {
    @NotBlank(message = "Room ID can not be blank")
    private String ID;
}
