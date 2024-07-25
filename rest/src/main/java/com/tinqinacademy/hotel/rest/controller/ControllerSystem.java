package com.tinqinacademy.hotel.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.hotel.api.enums.BathRoom;
import com.tinqinacademy.hotel.api.enums.Bed;
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
import com.tinqinacademy.hotel.api.model.operations.user.availablecheck.AvailableInput;
import com.tinqinacademy.hotel.api.model.operations.user.availablecheck.AvailableOutput;
import com.tinqinacademy.hotel.api.model.operations.user.book.BookInput;
import com.tinqinacademy.hotel.api.model.operations.user.book.BookOutput;
import com.tinqinacademy.hotel.api.model.operations.user.displayroom.DisplayRoomInput;
import com.tinqinacademy.hotel.api.model.operations.user.displayroom.DisplayRoomOutput;
import com.tinqinacademy.hotel.api.model.operations.user.register.RegisterInput;
import com.tinqinacademy.hotel.api.model.operations.user.register.RegisterOutput;
import com.tinqinacademy.hotel.api.model.operations.user.unbook.UnbookInput;
import com.tinqinacademy.hotel.api.model.operations.user.unbook.UnbookOutput;
import com.tinqinacademy.hotel.core.services.RoomSystemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ControllerSystem {
    private RoomSystemService roomSystemService;
    private ObjectMapper objectMapper;
    @Autowired
    public ControllerSystem(RoomSystemService roomSystemService, ObjectMapper objectMapper) {
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
    public ResponseEntity<AvailableOutput> checkAvailable(@RequestParam LocalDate startDate
            , @RequestParam LocalDate endDate
            , @RequestParam List<String> bedList
            , @RequestParam String bathRoomType) {
        AvailableInput availableInput = AvailableInput.builder()
                .startDate(startDate)
                .endDate(endDate)
                .bedList(bedList.stream().map(Bed::getByCode).toList())
                .bathRoom(BathRoom.getByCode(bathRoomType))
                .build();
        return ResponseEntity.ok(roomSystemService.checkAvailability(availableInput));

    }
    @GetMapping("hotel/{roomID}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room is displayed"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request")
    })
    @Operation(summary = "Display all info regarding a room")
    public ResponseEntity<DisplayRoomOutput> display(@PathVariable String roomID){
        DisplayRoomInput displayRoomInput = DisplayRoomInput.builder()
                .roomID(roomID)
                .build();
        return ResponseEntity.ok(roomSystemService.displayRoom(displayRoomInput));

    }
    @PostMapping("hotel/{roomID}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room is available"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request")
    })
    @Operation(summary = "Makes a booking")
    public ResponseEntity<BookOutput> book(@PathVariable String roomID,@Valid @RequestBody BookInput request){
    BookInput bookInput = request.toBuilder()
            .roomID(roomID)
            .build();
    return ResponseEntity.ok(roomSystemService.bookRoom(bookInput));

    }

    @DeleteMapping("hotel/{bookingID}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room is available"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request"),
            @ApiResponse(responseCode = "404", description = "Server was not found")
    })
    @Operation(summary = "Removes a booking")
    public ResponseEntity<UnbookOutput> unbook(@PathVariable String bookingID){
        UnbookInput unbookInput = UnbookInput.builder()
                .bookId(bookingID)
                .build();
        return ResponseEntity.ok(roomSystemService.unBookRoom(unbookInput));

    }

    @PostMapping("hotel/register")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room is registered"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request"),
            @ApiResponse(responseCode = "404", description = "Server was not found")
    })
    @Operation(summary = "Registers a person")
    public ResponseEntity<RegisterOutput> register(@Valid @RequestBody RegisterInput registerInput){
        return ResponseEntity.ok(roomSystemService.registerPerson(registerInput));
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
                .roomID(roomID)
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
    public ResponseEntity<AdminDeleteOutput> adminDelete(@PathVariable String roomID) {
        AdminDeleteInput adminDeleteInput = AdminDeleteInput.builder()
                .ID(roomID)
                .build();
        return ResponseEntity.ok(roomSystemService.adminDelete(adminDeleteInput));
    }

}
