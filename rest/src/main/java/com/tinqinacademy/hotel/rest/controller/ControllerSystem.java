package com.tinqinacademy.hotel.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.hotel.api.enums.BathRoom;
import com.tinqinacademy.hotel.api.model.operations.admin.create.AdminCreateInput;
import com.tinqinacademy.hotel.api.model.operations.admin.create.AdminCreateOutput;
import com.tinqinacademy.hotel.api.model.operations.admin.delete.AdminDeleteInput;
import com.tinqinacademy.hotel.api.model.operations.admin.delete.AdminDeleteOutput;
import com.tinqinacademy.hotel.api.model.operations.admin.partialupdate.AdminPartialUpdateInput;
import com.tinqinacademy.hotel.api.model.operations.admin.partialupdate.AdminPartialUpdateOutput;
import com.tinqinacademy.hotel.api.model.operations.admin.register.AdminRegisterInput;
import com.tinqinacademy.hotel.api.model.operations.admin.register.AdminRegisterOutput;
import com.tinqinacademy.hotel.api.model.operations.admin.update.AdminUpdateInput;
import com.tinqinacademy.hotel.api.model.operations.admin.update.AdminUpdateOutput;
import com.tinqinacademy.hotel.api.model.operations.user.availablecheck.UserAvailableInput;
import com.tinqinacademy.hotel.api.model.operations.user.availablecheck.UserAvailableOutput;
import com.tinqinacademy.hotel.api.model.operations.user.book.UserBookInput;
import com.tinqinacademy.hotel.api.model.operations.user.book.UserBookOutput;
import com.tinqinacademy.hotel.api.model.operations.user.displayroom.UserDisplayRoomInput;
import com.tinqinacademy.hotel.api.model.operations.user.displayroom.UserDisplayRoomOutput;
import com.tinqinacademy.hotel.api.model.operations.user.register.UserRegisterInput;
import com.tinqinacademy.hotel.api.model.operations.user.register.UserRegisterOutput;
import com.tinqinacademy.hotel.api.model.operations.user.unbook.UserUnbookInput;
import com.tinqinacademy.hotel.api.model.operations.user.unbook.UserUnbookOutput;
import com.tinqinacademy.hotel.core.services.RoomSystemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;


@RestController
@RequestMapping("/api")
public class ControllerSystem {
    private final RoomSystemService roomSystemService;
    private final ObjectMapper objectMapper;
    @Autowired
    public ControllerSystem(final RoomSystemService roomSystemService,final ObjectMapper objectMapper) {
        this.roomSystemService = roomSystemService;
        this.objectMapper = objectMapper;

    }
    @GetMapping("hotel/available")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room is available"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request")
    })
    @Operation(summary = "Checks availability")
    public ResponseEntity<UserAvailableOutput> checkAvailable(@RequestParam LocalDate startDate
            , @RequestParam LocalDate endDate
            , @RequestParam String bed
            , @RequestParam String bathRoomType) {
        UserAvailableInput userAvailableInput = UserAvailableInput.builder()
                .startDate(startDate)
                .endDate(endDate)
                .bed(bed)
                .bathRoom(BathRoom.getByCode(bathRoomType))
                .build();
        return ResponseEntity.ok(roomSystemService.checkAvailability(userAvailableInput));

    }
    @GetMapping("hotel/{roomID}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room is displayed"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request")
    })
    @Operation(summary = "Display all info regarding a room")
    public ResponseEntity<UserDisplayRoomOutput> display(@PathVariable UUID roomID){
        UserDisplayRoomInput userDisplayRoomInput = UserDisplayRoomInput.builder()
                .roomID(roomID)
                .build();
        return ResponseEntity.ok(roomSystemService.displayRoom(userDisplayRoomInput));

    }
    @PostMapping("hotel/{roomID}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room is available"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request")
    })
    @Operation(summary = "Makes a booking")
    public ResponseEntity<UserBookOutput> book(@PathVariable UUID roomID, @Valid @RequestBody UserBookInput request){
    UserBookInput userBookInput = request.toBuilder()
            .roomID(roomID)
            .build();
    return ResponseEntity.ok(roomSystemService.bookRoom(userBookInput));

    }

    @DeleteMapping("hotel/{bookingID}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room is available"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request"),
            @ApiResponse(responseCode = "404", description = "Server was not found")
    })
    @Operation(summary = "Removes a booking")
    public ResponseEntity<UserUnbookOutput> unbook(@PathVariable UUID bookingID){
        UserUnbookInput userUnbookInput = UserUnbookInput.builder()
                .bookId(bookingID)
                .build();
        return ResponseEntity.ok(roomSystemService.unBookRoom(userUnbookInput));

    }

    @PostMapping("hotel/register")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room is registered"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request"),
            @ApiResponse(responseCode = "404", description = "Server was not found")
    })
    @Operation(summary = "Registers a person")
    public ResponseEntity<UserRegisterOutput> register(@Valid @RequestBody UserRegisterInput userRegisterInput){
        return ResponseEntity.ok(roomSystemService.registerPerson(userRegisterInput));
    }

    @GetMapping("system/register")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room is registered by system"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request"),
            @ApiResponse(responseCode = "404", description = "Server was not found")
    })
    @Operation(summary = "Administrative register")
    public ResponseEntity<AdminRegisterOutput> adminRegister(@RequestParam LocalDate startDate,
                                                             @RequestParam LocalDate endDate,
                                                             @RequestParam String firstName,
                                                             @RequestParam String lastname,
                                                             @RequestParam String phoneNumber,
                                                             @RequestParam String idNumber,
                                                             @RequestParam String validity,
                                                             @RequestParam String authority,
                                                             @RequestParam LocalDate issueDate,
                                                             @RequestParam String roomID){
        AdminRegisterInput adminRegisterInput = AdminRegisterInput.builder()
                .startDate(startDate)
                .endDate(endDate)
                .firstName(firstName)
                .lastName(lastname)
                .phone(phoneNumber)
                .idNumber(idNumber)
                .validity(validity)
                .authority(authority)
                .issueDate(issueDate)
                .roomID(roomID)
                .build();
        return ResponseEntity.ok(roomSystemService.adminRegister(adminRegisterInput));
    }

    @PostMapping("/system/room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room is create by system"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request"),
            @ApiResponse(responseCode = "404", description = "Server was not found")
    })
    @Operation(summary = "Admin creates room")
    public ResponseEntity<AdminCreateOutput> adminCreate(@Valid @RequestBody AdminCreateInput adminCreateInput) {
        return ResponseEntity.ok(roomSystemService.adminCreate(adminCreateInput));
    }

    @PutMapping("/system/room/{roomID}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room is updated by system"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request"),
            @ApiResponse(responseCode = "404", description = "Server was not found")
    })
    @Operation(summary = "Admin updates room")
    public ResponseEntity<AdminUpdateOutput> adminUpdate(@PathVariable String roomID,@Valid @RequestBody AdminUpdateInput request) {
        AdminUpdateInput adminUpdateInput = request.toBuilder()
                .roomID(UUID.fromString(roomID))
                .build();
        return ResponseEntity.ok(roomSystemService.adminUpdate(adminUpdateInput));
    }

    @PatchMapping("/system/room/{roomID}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room is updated by system"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request"),
            @ApiResponse(responseCode = "404", description = "Server was not found")
    })
    @Operation(summary = "Admin partially updates the system")
    public ResponseEntity<AdminPartialUpdateOutput> adminPartialUpdate(@PathVariable String roomID,@Valid @RequestBody AdminPartialUpdateInput request) {
        AdminPartialUpdateInput adminPartialUpdateInput = request.toBuilder()
                .roomID(roomID)
                .build();
        return ResponseEntity.ok(roomSystemService.adminPartialUpdate(adminPartialUpdateInput));
    }
    @DeleteMapping("system/room/{roomID}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room is deleted"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request"),
            @ApiResponse(responseCode = "404", description = "Server was not found")
    })
    @Operation(summary = "Admin deletes a room")
    public ResponseEntity<AdminDeleteOutput> adminDelete(@PathVariable UUID roomID) {
        AdminDeleteInput adminDeleteInput = AdminDeleteInput.builder()
                .ID(roomID)
                .build();
        return ResponseEntity.ok(roomSystemService.adminDelete(adminDeleteInput));
    }

}
