package com.tinqinacademy.hotel.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.hotel.api.model.operations.user.availablecheck.UserAvailableInput;
import com.tinqinacademy.hotel.api.model.operations.user.availablecheck.UserAvailableOperation;
import com.tinqinacademy.hotel.api.model.operations.user.book.UserBookInput;
import com.tinqinacademy.hotel.api.model.operations.user.book.UserBookOperation;
import com.tinqinacademy.hotel.api.model.operations.user.displayroom.UserDisplayRoomInput;
import com.tinqinacademy.hotel.api.model.operations.user.displayroom.UserDisplayRoomOperation;
import com.tinqinacademy.hotel.api.model.operations.user.unbook.UserUnbookInput;
import com.tinqinacademy.hotel.api.model.operations.user.unbook.UserUnbookOperation;
import com.tinqinacademy.hotel.api.constants.MappingsConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController extends BaseController{
    private final ObjectMapper objectMapper;
    private final UserAvailableOperation userAvailableOperation;
    private final UserBookOperation userBookOperation;
    private final UserDisplayRoomOperation userDisplayRoomOperation;
    private final UserUnbookOperation userUnbookOperation;

    @GetMapping(MappingsConstants.userAvailability)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room is available"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request")
    })
    @Operation(summary = "Checks availability")
    public ResponseEntity<?> checkAvailable(@RequestParam LocalDate startDate
            , @RequestParam LocalDate endDate
            , @RequestParam String bed
            , @RequestParam String bathRoomType) {
        UserAvailableInput userAvailableInput = UserAvailableInput.builder()
                .startDate(startDate)
                .endDate(endDate)
                .bed(bed)
                .bathRoom(bathRoomType)
                .build();
        return handleOperation(userAvailableOperation.process(userAvailableInput));

    }
    @GetMapping(MappingsConstants.userDisplay)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room is displayed"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request")
    })
    @Operation(summary = "Display all info regarding a room")
    public ResponseEntity<?> display(@PathVariable UUID roomID){
        UserDisplayRoomInput userDisplayRoomInput = UserDisplayRoomInput.builder()
                .roomID(roomID)
                .build();
        return handleOperation(userDisplayRoomOperation.process(userDisplayRoomInput));

    }
    @PostMapping(MappingsConstants.userBook)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room is available"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request")
    })
    @Operation(summary = "Makes a booking")
    public ResponseEntity<?> book(@PathVariable UUID roomID, @RequestBody UserBookInput request){
        UserBookInput userBookInput = request.toBuilder()
                .roomID(roomID)
                .build();
        return handleOperation(userBookOperation.process(userBookInput));
    }
    @DeleteMapping(MappingsConstants.userUnBook)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room is available"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request"),
            @ApiResponse(responseCode = "404", description = "Server was not found")
    })
    @Operation(summary = "Removes a booking")
    public ResponseEntity<?> unbook(@PathVariable String reservationID){
        UserUnbookInput userUnbookInput = UserUnbookInput.builder()
                .bookId(UUID.fromString(reservationID))
                .build();
        return handleOperation(userUnbookOperation.process(userUnbookInput));
    }
}
