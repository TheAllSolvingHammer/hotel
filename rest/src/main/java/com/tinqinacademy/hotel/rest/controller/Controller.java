package com.tinqinacademy.hotel.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.hotel.api.enums.Bed;
import com.tinqinacademy.hotel.api.model.previous.getroom.GetInput;
import com.tinqinacademy.hotel.api.model.previous.getroom.GetOutput;
import com.tinqinacademy.hotel.api.model.previous.room.RoomInput;
import com.tinqinacademy.hotel.api.model.previous.room.RoomOutput;
import com.tinqinacademy.hotel.core.services.RoomService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Controller {
    private final ObjectMapper mapper;
 private final RoomService room;
 @Autowired
 public Controller(RoomService room, ObjectMapper mapper) {
     this.room = room;
     this.mapper = mapper;
 }
    @GetMapping("/check")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Will check if room is available "),
            @ApiResponse(responseCode = "404", description = "Error to be displayed if a room is not available in the list"),
            @ApiResponse(responseCode = "400", description = "bad request"),
            @ApiResponse(responseCode = "401", description = "unauthorized")
    })
    public String check() {
     return room.checkRoom();
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Will book room "),
            @ApiResponse(responseCode = "404", description = "Error to be displayed if a room is not available in the list"),
            @ApiResponse(responseCode = "400", description = "bad request"),
            @ApiResponse(responseCode = "401", description = "unauthorized")
    })
    @PostMapping("/book")
    public String book() {
        return room.bookRoom();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Will edit a room "),
            @ApiResponse(responseCode = "404", description = "Error to be displayed if a room is not available in the list"),
            @ApiResponse(responseCode = "400", description = "bad request"),
            @ApiResponse(responseCode = "401", description = "unauthorized")
    })
    @PutMapping("/edit")
    public String edit() {
        return room.editRoom();
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Will remove if room is available "),
            @ApiResponse(responseCode = "404", description = "Error to be displayed if a room is not available in the list"),
            @ApiResponse(responseCode = "400", description = "bad request"),
            @ApiResponse(responseCode = "401", description = "unauthorized")
    })
    @DeleteMapping("/remove")
    public String remove() {
        return room.removeRoom();
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Will add "),
            @ApiResponse(responseCode = "404", description = "Error to be displayed if a room is not available in the list"),
            @ApiResponse(responseCode = "400", description = "bad request"),
            @ApiResponse(responseCode = "401", description = "unauthorized")
    })
    @PostMapping("/add")

    public RoomOutput add(@RequestBody RoomInput input) {
       return room.addRoom(input);

    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get room "),
            @ApiResponse(responseCode = "404", description = "Error to be displayed if a room is not available in the list"),
            @ApiResponse(responseCode = "400", description = "bad request"),
            @ApiResponse(responseCode = "401", description = "unauthorized")
    })
    @GetMapping("/{floor}")
    public GetOutput getRoom(@PathVariable Integer floor, @RequestParam String bedType){
     GetInput getInput = GetInput.builder()
             .bedType(Bed.getByCode(bedType))
             .floor(floor)
             .build();
     return room.getRoom(getInput);
    }

}
