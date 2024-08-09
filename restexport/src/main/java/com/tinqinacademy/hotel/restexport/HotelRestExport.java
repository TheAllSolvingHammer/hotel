package com.tinqinacademy.hotel.restexport;


import com.tinqinacademy.hotel.api.model.operations.admin.create.AdminCreateInput;
import com.tinqinacademy.hotel.api.model.operations.admin.partialupdate.AdminPartialUpdateInput;
import com.tinqinacademy.hotel.api.model.operations.admin.update.AdminUpdateInput;
import com.tinqinacademy.hotel.api.model.operations.user.book.UserBookInput;
import com.tinqinacademy.hotel.api.model.operations.user.register.UserRegisterInput;
import com.tinqinacademy.hotel.core.constants.MappingsConstants;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;


@FeignClient(value = "hotel", url = "${hotel",configuration = ClientFactory.class)
public interface HotelRestExport {
    @RequestLine("GET "+ MappingsConstants.userAvailability)
    ResponseEntity<?> checkAvailable(@RequestParam LocalDate startDate
            , @RequestParam LocalDate endDate
            , @RequestParam String bed
            , @RequestParam String bathRoomType);
    @RequestLine("GET "+ MappingsConstants.userDisplay)
    ResponseEntity<?> display(@PathVariable UUID roomID);
    @RequestLine("POST "+ MappingsConstants.userBook)
    ResponseEntity<?> book(@PathVariable UUID roomID
            , @RequestBody UserBookInput request);
    @RequestLine("DELETE "+ MappingsConstants.userUnBook)
    ResponseEntity<?> unbook(@PathVariable String reservationID);

    @RequestLine("POST "+ MappingsConstants.adminRegister)
    ResponseEntity<?> register( @RequestBody UserRegisterInput userRegisterInput);

    @RequestLine("GET "+ MappingsConstants.adminRegister)
    ResponseEntity<?> adminRegister(@RequestParam(required = false) LocalDate startDate,
                                           @RequestParam(required = false) LocalDate endDate,
                                           @RequestParam(required = false) String firstName,
                                           @RequestParam(required = false) String lastname,
                                           @RequestParam(required = false) String phoneNumber,
                                           @RequestParam(required = false) String idNumber,
                                           @RequestParam(required = false) LocalDate validity,
                                           @RequestParam(required = false) String authority,
                                           @RequestParam(required = false) LocalDate issueDate,
                                           @RequestParam(required = false) String roomID);

    @RequestLine("POST "+ MappingsConstants.adminCreate)
    ResponseEntity<?> adminCreate(@RequestBody AdminCreateInput adminCreateInput);

    @RequestLine("PUT "+ MappingsConstants.adminUpdate)
    ResponseEntity<?> adminUpdate(@PathVariable String roomID
            , @RequestBody AdminUpdateInput request);

    @RequestLine("PATCH "+ MappingsConstants.adminPartialUpdate)
    ResponseEntity<?> adminPartialUpdate(@PathVariable UUID roomID
            , @RequestBody AdminPartialUpdateInput request);

    @RequestLine("DELETE "+ MappingsConstants.adminDelete)
    ResponseEntity<?> adminDelete(@PathVariable UUID roomID);

}
