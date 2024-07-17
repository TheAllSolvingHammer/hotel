package com.tinqinacademy.hotel.api.model.operations.user.availablecheck;


import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class AvailableOutput {
   private List<String> id;
}
