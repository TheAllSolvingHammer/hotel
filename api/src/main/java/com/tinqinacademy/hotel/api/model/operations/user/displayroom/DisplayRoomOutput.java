package com.tinqinacademy.hotel.api.model.operations.user.displayroom;

import com.tinqinacademy.hotel.api.enums.BathRoom;
import com.tinqinacademy.hotel.api.enums.Bed;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class DisplayRoomOutput {
    private UUID ID;
    private BigDecimal price;
    private Integer floor;
    private List<Bed> bedSize;
    private BathRoom bathRoom;
    private List<List<LocalDate>> datesOccupied;
}
