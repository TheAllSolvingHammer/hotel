package com.tinqinacademy.hotel.core.services;


import com.tinqinacademy.hotel.api.model.previous.getroom.GetInput;
import com.tinqinacademy.hotel.api.model.previous.getroom.GetOutput;
import com.tinqinacademy.hotel.api.model.previous.room.RoomInput;
import com.tinqinacademy.hotel.api.model.previous.room.RoomOutput;

public interface RoomService {
    RoomOutput addRoom(RoomInput roomInput);
    String removeRoom();
    String editRoom();
    String checkRoom();
    String bookRoom();
    GetOutput getRoom(GetInput getInput);
}
