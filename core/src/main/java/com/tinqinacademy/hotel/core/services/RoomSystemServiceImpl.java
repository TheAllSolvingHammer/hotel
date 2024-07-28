package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.enums.BathRoom;
import com.tinqinacademy.hotel.api.enums.Bed;
import com.tinqinacademy.hotel.api.exceptions.InputException;
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
import com.tinqinacademy.hotel.persistence.entities.BedEntity;
import com.tinqinacademy.hotel.persistence.entities.ReservationEntity;
import com.tinqinacademy.hotel.persistence.entities.RoomEntity;
import com.tinqinacademy.hotel.persistence.entities.UserEntity;
import com.tinqinacademy.hotel.persistence.enums.BathTypes;
import com.tinqinacademy.hotel.persistence.repositorynew.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
public class RoomSystemServiceImpl implements RoomSystemService {
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final BedRepository bedRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoomSystemServiceImpl(RoomRepository roomRepository
            , GuestRepository guestRepository
            , BedRepository bedRepository
            , UserRepository userRepository
            , ReservationRepository reservationRepository
            , JdbcTemplate jdbcTemplate) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.bedRepository = bedRepository;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public AvailableOutput checkAvailability(AvailableInput availableInput) {
        log.info("Start check availability: {}", availableInput);
        Bed bed= Bed.getByCode(availableInput.getBed());
        if(bed.equals(Bed.UNKNOWN)){
            log.info("Finished bed check. Bed is unknown{}", availableInput.getBed());
            throw new InputException("Bed is unknown");
        }
        List<UUID> rooms =roomRepository.findByCustom(availableInput.getEndDate()
                ,availableInput.getStartDate()
                ,availableInput.getBathRoom().toString().toUpperCase()
                ,availableInput.getBed().toUpperCase());
        AvailableOutput availableOutput= AvailableOutput.builder()
                .id(rooms)
                .build();
        log.info("End check availability: {}", availableOutput);
        return availableOutput;
    }

    @Override
    public DisplayRoomOutput displayRoom(DisplayRoomInput displayRoomInput) {
        log.info("Start display room: {}", displayRoomInput);
        //todo
        //fixme
        DisplayRoomOutput displayRoomOutput = DisplayRoomOutput.builder()
                .ID("S123")
                .price(new BigDecimal("3424"))
                .floor(4)
                .bedSize(Bed.getByCode("singlekingsize"))
                .bathRoom(BathRoom.getByCode("private"))
                .bedCount(2)
                .datesOccupied(List.of(LocalDate.of(2021,2,16)
                ,LocalDate.of(2001,9,11)))
                .build();
        log.info("End display room: {}", displayRoomOutput);
        return displayRoomOutput;

    }

    @Override
    public BookOutput bookRoom(BookInput bookInput) {
        log.info("Start book room: {}", bookInput);



        UserEntity userEntity = UserEntity.builder()
                .id(UUID.randomUUID())
                .email(bookInput.getEmail())
                .firstName(bookInput.getFirstName())
                .lastName(bookInput.getLastName())
                .phoneNumber(bookInput.getPhoneNo())
                .birthday(bookInput.getDateOfBirth())
                .build();


        ReservationEntity reservationEntity = ReservationEntity.builder()
                .room(roomRepository.getReferenceById(bookInput.getRoomID()))

                .build();


        BookOutput bookOutput = BookOutput.builder()
                .message("Successfully booked a room")
                .build();
        log.info("End book room: {}", bookOutput);
        return bookOutput;
    }

    @Override
    public UnbookOutput unBookRoom(UnbookInput unBookInput) {
        log.info("Start unbook room: {}", unBookInput);
        UnbookOutput unbookOutput = UnbookOutput.builder()
                .message("Successfully unbooked a room")
                .build();
        log.info("End unbook room: {}", unbookOutput);
        return unbookOutput;
    }

    @Override
    public RegisterOutput registerPerson(RegisterInput registerInput) {
        log.info("Start register person: {}", registerInput);
        RegisterOutput registerOutput = RegisterOutput.builder()
                .message("Successfully registered room")
                .build();
        log.info("End register person: {}", registerOutput);
        return registerOutput;
    }

    @Override
    public AdminRegisterOutput adminRegister(AdminRegisterInput adminInfoInput) {
        log.info("Start admin info: {}", adminInfoInput);
        if(adminInfoInput.getRoomID().equalsIgnoreCase("5A")){
            throw new InputException("Invalid room for registration");
        }
        AdminRegisterOutput adminRegisterOutput = AdminRegisterOutput.builder()
                .data(new ArrayList<>(Arrays.asList("1","2","3")))
                .startDate(adminInfoInput.getStartDate())
                .endDate(adminInfoInput.getEndDate())
                .firstName(adminInfoInput.getFirstName())
                .lastName(adminInfoInput.getLastName())
                .phone(adminInfoInput.getPhone())
                .idNumber(adminInfoInput.getIdNumber())
                .validity(adminInfoInput.getValidity())
                .authority(adminInfoInput.getAuthority())
                .issueDate(adminInfoInput.getIssueDate())
                .build();
        log.info("End admin info: {}", adminRegisterOutput);
        return adminRegisterOutput;
    }

    @Override
    public AdminCreateOutput adminCreate(AdminCreateInput adminCreateInput) {
        log.info("Start admin create room: {}", adminCreateInput);
        //fixme
        //todo
        BathRoom bathRoom=BathRoom.getByCode(adminCreateInput.getBathRoom());
        if(bathRoom.equals(BathRoom.UNKNOWN)){
            throw new InputException("Bathroom is unknown");
        }

        List<BedEntity> bedEntities = bedRepository.findAll();
        for(String s:adminCreateInput.getBedType())
        {

            if(bedEntities.contains(Bed.getByCode(s))){

            }
        }
        RoomEntity roomEntity = RoomEntity.builder()
                .id(UUID.randomUUID())
                .roomNumber(adminCreateInput.getRoomNumber())
                .bathTypes(BathTypes.getByCode(adminCreateInput.getBathRoom()))
                //.bedList()

                .build();
        AdminCreateOutput adminCreateOutput = AdminCreateOutput.builder()
                .ID(roomEntity.getId())
                .build();
        log.info("End admin create room: {}", adminCreateOutput);
        return adminCreateOutput;
    }

    @Override
    public AdminUpdateOutput adminUpdate(AdminUpdateInput adminUpdateInput) {
        log.info("Start admin update room: {}", adminUpdateInput);
        if(adminUpdateInput.getRoomID().equalsIgnoreCase("5A"))
            throw new InputException("Invalid room for update");
        AdminUpdateOutput adminUpdateOutput = AdminUpdateOutput.builder()
                .ID("239283")
                .build();
        log.info("End admin update room: {}", adminUpdateOutput);
        return adminUpdateOutput;
    }

    @Override
    public AdminPartialUpdateOutput adminPartialUpdate(AdminPartialUpdateInput adminPartialUpdateInput) {
        log.info("Start admin partial update room: {}", adminPartialUpdateInput);
        if(adminPartialUpdateInput.getRoomID().equalsIgnoreCase("5A"))
            throw new InputException("Invalid room for partial update");
        AdminPartialUpdateOutput adminPartialUpdateOutput = AdminPartialUpdateOutput.builder()
                .ID("23234")
                .build();
        log.info("End admin partial update room: {}", adminPartialUpdateOutput);
        return adminPartialUpdateOutput;
    }

    @Override
    public AdminDeleteOutput adminDelete(AdminDeleteInput adminDeleteInput) {
        log.info("Start admin delete room: {}", adminDeleteInput);
        if(adminDeleteInput.getID().equalsIgnoreCase("5A")){
            throw new InputException("Invalid room for deletion");
        }
        AdminDeleteOutput adminDeleteOutput = AdminDeleteOutput.builder()
                .message("Successfully delete room with ID: "+adminDeleteInput.getID())
                .build();
        log.info("End admin delete room: {}", adminDeleteOutput);
        return adminDeleteOutput;
    }
}
