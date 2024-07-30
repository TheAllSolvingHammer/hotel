package com.tinqinacademy.hotel.api.model.operations.user.availablecheck;


import com.tinqinacademy.hotel.api.base.OperationOutput;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class UserAvailableOutput implements OperationOutput {
   private List<UUID> id;
}
