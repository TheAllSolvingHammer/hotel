package com.tinqinacademy.hotel.api.model.previous.getroom;

import com.tinqinacademy.hotel.api.enums.Beds;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class GetOutput {
    private Integer floor;
    private Beds bedType;
}
