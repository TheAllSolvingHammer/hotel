package com.tinqinacademy.hotel.api.model.operations.user.availablecheck;


import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class AvailableOutput {
   private List<UUID> id;
}
