package com.tinqinacademy.hotel.api.model.operations.user.displayroom;

import com.tinqinacademy.hotel.api.enums.BathRoomType;
import com.tinqinacademy.hotel.api.enums.Beds;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class DisplayRoomOutput {
    private String ID;
    private BigDecimal price;
    private Integer floor;
    private Beds bedSize;
    private BathRoomType bathRoomType;
    private Integer bedCount;
    private List<LocalDate> datesOccupied;
}
