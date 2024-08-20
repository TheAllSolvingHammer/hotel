package com.tinqinacademy.hotel.restexport;


import com.tinqinacademy.hotel.api.model.operations.admin.create.AdminCreateInput;
import com.tinqinacademy.hotel.api.model.operations.admin.create.AdminCreateOutput;
import com.tinqinacademy.hotel.api.model.operations.admin.delete.AdminDeleteOutput;
import com.tinqinacademy.hotel.api.model.operations.admin.partialupdate.AdminPartialUpdateInput;
import com.tinqinacademy.hotel.api.model.operations.admin.partialupdate.AdminPartialUpdateOutput;
import com.tinqinacademy.hotel.api.model.operations.admin.register.AdminRegisterOutput;
import com.tinqinacademy.hotel.api.model.operations.admin.update.AdminUpdateInput;
import com.tinqinacademy.hotel.api.model.operations.admin.update.AdminUpdateOutput;
import com.tinqinacademy.hotel.api.model.operations.user.availablecheck.UserAvailableOutput;
import com.tinqinacademy.hotel.api.model.operations.user.book.UserBookInput;
import com.tinqinacademy.hotel.api.model.operations.user.book.UserBookOutput;
import com.tinqinacademy.hotel.api.model.operations.user.displayroom.UserDisplayRoomOutput;
import com.tinqinacademy.hotel.api.model.operations.user.register.UserRegisterInput;
import com.tinqinacademy.hotel.api.model.operations.user.register.UserRegisterOutput;
import com.tinqinacademy.hotel.api.model.operations.user.unbook.UserUnbookOutput;
import com.tinqinacademy.hotel.core.constants.MappingsConstants;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.UUID;


@FeignClient(value = "hotel", url = "http://localhost:8080",configuration = ClientFactory.class)
public interface HotelRestExport {
    @RequestLine("GET "+ MappingsConstants.userAvailability)
    UserAvailableOutput checkAvailable(@RequestParam LocalDate startDate
            , @RequestParam LocalDate endDate
            , @RequestParam String bed
            , @RequestParam String bathRoomType);

    @RequestLine("GET "+ MappingsConstants.userDisplay)
    UserDisplayRoomOutput display(@PathVariable UUID roomID);
    @RequestLine("POST "+ MappingsConstants.userBook)
    UserBookOutput book(@PathVariable UUID roomID
            , @RequestBody UserBookInput request);
    @RequestLine("DELETE "+ MappingsConstants.userUnBook)
    UserUnbookOutput unbook(@PathVariable String reservationID);

    @RequestLine("POST "+ MappingsConstants.userRegister)
    UserRegisterOutput register(@RequestBody UserRegisterInput userRegisterInput);

    @RequestLine("GET "+ MappingsConstants.adminRegister)
    AdminRegisterOutput adminRegister(@RequestParam(required = false) LocalDate startDate,
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
    AdminCreateOutput adminCreate(@RequestBody AdminCreateInput adminCreateInput);

    @RequestLine("PUT "+ MappingsConstants.adminUpdate)
    AdminUpdateOutput adminUpdate(@PathVariable String roomID
            , @RequestBody AdminUpdateInput request);

    @RequestLine("PATCH "+ MappingsConstants.adminPartialUpdate)
    AdminPartialUpdateOutput adminPartialUpdate(@PathVariable UUID roomID
            , @RequestBody AdminPartialUpdateInput request);

    @RequestLine("DELETE "+ MappingsConstants.adminDelete)
    AdminDeleteOutput adminDelete(@PathVariable UUID roomID);

}
