package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.model.previous.getroom.GetInput;
import com.tinqinacademy.hotel.api.model.previous.getroom.GetOutput;
import com.tinqinacademy.hotel.api.model.previous.room.RoomInput;
import com.tinqinacademy.hotel.api.model.previous.room.RoomOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class RoomServiceImpl implements RoomService {
    @Override
    public RoomOutput addRoom(RoomInput roomInput) {
        log.info("Start addRoom {}",roomInput);
        RoomOutput roomOutput = RoomOutput.builder()
                .id(roomInput.getId())
                .bedCount(roomInput.getBedCount())
                .bed(roomInput.getBed())
                .floor(roomInput.getFloor())
                .price(roomInput.getPrice())
                .roomType(roomInput.getRoomType())
                .build();
        log.info("End addRoom: {}",roomOutput);


        return roomOutput;
    }
    @Override
    public String removeRoom() {
        return ("removed room");
    }

    @Override
    public String editRoom() {
        return "edited";
    }

    @Override
    public String checkRoom() {
        return "unavailable";
    }

    @Override
    public String bookRoom() {
        return("booked room");
    }

    @Override
    public GetOutput getRoom(GetInput getInput) {
        log.info("Start getRoom {}",getInput);
        return GetOutput.builder()
                .bedType(getInput.getBedType())
                .floor(getInput.getFloor())
                .build();

    }
}
