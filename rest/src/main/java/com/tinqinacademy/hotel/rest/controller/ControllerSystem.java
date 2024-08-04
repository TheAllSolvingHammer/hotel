package com.tinqinacademy.hotel.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.hotel.api.model.operations.admin.create.AdminCreateInput;
import com.tinqinacademy.hotel.api.model.operations.admin.create.AdminCreateOperation;
import com.tinqinacademy.hotel.api.model.operations.admin.delete.AdminDeleteInput;
import com.tinqinacademy.hotel.api.model.operations.admin.delete.AdminDeleteOperation;
import com.tinqinacademy.hotel.api.model.operations.admin.partialupdate.AdminPartialUpdateInput;
import com.tinqinacademy.hotel.api.model.operations.admin.partialupdate.AdminPartialUpdateOperation;
import com.tinqinacademy.hotel.api.model.operations.admin.register.AdminRegisterInput;
import com.tinqinacademy.hotel.api.model.operations.admin.register.AdminRegisterOperation;
import com.tinqinacademy.hotel.api.model.operations.admin.update.AdminUpdateInput;
import com.tinqinacademy.hotel.api.model.operations.admin.update.AdminUpdateOperation;
import com.tinqinacademy.hotel.api.model.operations.user.availablecheck.UserAvailableInput;
import com.tinqinacademy.hotel.api.model.operations.user.availablecheck.UserAvailableOperation;
import com.tinqinacademy.hotel.api.model.operations.user.book.UserBookInput;
import com.tinqinacademy.hotel.api.model.operations.user.book.UserBookOperation;
import com.tinqinacademy.hotel.api.model.operations.user.displayroom.UserDisplayRoomInput;
import com.tinqinacademy.hotel.api.model.operations.user.displayroom.UserDisplayRoomOperation;
import com.tinqinacademy.hotel.api.model.operations.user.register.UserRegisterInput;
import com.tinqinacademy.hotel.api.model.operations.user.register.UserRegisterOperation;
import com.tinqinacademy.hotel.api.model.operations.user.unbook.UserUnbookInput;
import com.tinqinacademy.hotel.api.model.operations.user.unbook.UserUnbookOperation;
import com.tinqinacademy.hotel.rest.constants.MappingsConstants;
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
public class ControllerSystem extends BaseController {
    private final ObjectMapper objectMapper;
    private final UserAvailableOperation userAvailableOperation;
    private final UserBookOperation userBookOperation;
    private final UserDisplayRoomOperation userDisplayRoomOperation;
    private final UserRegisterOperation userRegisterOperation;
    private final UserUnbookOperation userUnbookOperation;
    private final AdminUpdateOperation adminUpdateOperation;
    private final AdminRegisterOperation adminRegisterOperation;
    private final AdminPartialUpdateOperation adminPartialUpdateOperation;
    private final AdminDeleteOperation adminDeleteOperation;
    private final AdminCreateOperation adminCreateOperation;


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

    @PostMapping(MappingsConstants.userRegister )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room is registered"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request"),
            @ApiResponse(responseCode = "404", description = "Server was not found")
    })
    @Operation(summary = "Registers a person")
    public ResponseEntity<?> register( @RequestBody UserRegisterInput userRegisterInput){
        return handleOperation(userRegisterOperation.process(userRegisterInput));
    }

    @GetMapping(MappingsConstants.adminRegister)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room is registered by system"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request"),
            @ApiResponse(responseCode = "404", description = "Server was not found")
    })
    @Operation(summary = "Administrative register")
    public ResponseEntity<?> adminRegister(@RequestParam LocalDate startDate,
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
        return handleOperation(adminRegisterOperation.process(adminRegisterInput));
    }

    @PostMapping(MappingsConstants.adminCreate)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room is create by system"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request"),
            @ApiResponse(responseCode = "404", description = "Server was not found")
    })
    @Operation(summary = "Admin creates room")
    public ResponseEntity<?> adminCreate(@RequestBody AdminCreateInput adminCreateInput) {
        return handleOperation(adminCreateOperation.process(adminCreateInput));
    }

    @PutMapping(MappingsConstants.adminUpdate)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room is updated by system"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request"),
            @ApiResponse(responseCode = "404", description = "Server was not found")
    })
    @Operation(summary = "Admin updates room")
    public ResponseEntity<?> adminUpdate(@PathVariable String roomID, @RequestBody AdminUpdateInput request) {
        AdminUpdateInput adminUpdateInput = request.toBuilder()
                .roomID(UUID.fromString(roomID))
                .build();
        return handleOperation(adminUpdateOperation.process(adminUpdateInput));
    }

    @PatchMapping(MappingsConstants.adminPartialUpdate)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room is updated by system"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request"),
            @ApiResponse(responseCode = "404", description = "Server was not found")
    })
    @Operation(summary = "Admin partially updates the system")
    public ResponseEntity<?> adminPartialUpdate(@PathVariable String roomID, @RequestBody AdminPartialUpdateInput request) {
        AdminPartialUpdateInput adminPartialUpdateInput = request.toBuilder()
                .roomID(roomID)
                .build();
        return handleOperation(adminPartialUpdateOperation.process(adminPartialUpdateInput));

    }
    @DeleteMapping(MappingsConstants.adminDelete)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room is deleted"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request"),
            @ApiResponse(responseCode = "404", description = "Server was not found")
    })
    @Operation(summary = "Admin deletes a room")
    public ResponseEntity<?> adminDelete(@PathVariable UUID roomID) {
        AdminDeleteInput adminDeleteInput = AdminDeleteInput.builder()
                .ID(roomID)
                .build();
    return handleOperation(adminDeleteOperation.process(adminDeleteInput));
    }

}
