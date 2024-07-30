package com.tinqinacademy.hotel.core.services;

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

public interface RoomSystemService {
    UserAvailableOutput checkAvailability(UserAvailableInput userAvailableInput);
    UserDisplayRoomOutput displayRoom(UserDisplayRoomInput userDisplayRoomInput);
    UserBookOutput bookRoom(UserBookInput userBookInput);
    UserUnbookOutput unBookRoom(UserUnbookInput unBookInputUser);
    UserRegisterOutput registerPerson(UserRegisterInput userRegisterInput);
    AdminRegisterOutput adminRegister(AdminRegisterInput adminInfoInput);
    AdminCreateOutput adminCreate(AdminCreateInput adminCreateInput);
    AdminUpdateOutput adminUpdate(AdminUpdateInput adminUpdateInput);
    AdminPartialUpdateOutput adminPartialUpdate(AdminPartialUpdateInput adminPartialUpdateInput);
    AdminDeleteOutput adminDelete(AdminDeleteInput adminDeleteInput);
}
