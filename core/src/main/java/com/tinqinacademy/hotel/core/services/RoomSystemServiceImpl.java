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
import com.tinqinacademy.hotel.persistence.entities.*;
import com.tinqinacademy.hotel.persistence.enums.BathTypes;
import com.tinqinacademy.hotel.persistence.enums.BedTypes;
import com.tinqinacademy.hotel.persistence.repositorynew.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class RoomSystemServiceImpl implements RoomSystemService {
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final BedRepository bedRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;




    @Override
    public UserAvailableOutput checkAvailability(UserAvailableInput userAvailableInput) {
        //MOVED
        log.info("Start check availability: {}", userAvailableInput);
        Bed bed= Bed.getByCode(userAvailableInput.getBed());

        List<UUID> rooms =roomRepository.findByCustom(userAvailableInput.getEndDate()
                , userAvailableInput.getStartDate()
                , userAvailableInput.getBathRoom().toString().toUpperCase()
                , userAvailableInput.getBed().toUpperCase());
        UserAvailableOutput userAvailableOutput = UserAvailableOutput.builder()
                .id(rooms)
                .build();
        log.info("End check availability: {}", userAvailableOutput);
        return userAvailableOutput;
    }

    @Override
    public UserDisplayRoomOutput displayRoom(UserDisplayRoomInput userDisplayRoomInput) {
        //MOVED
        log.info("Start display room: {}", userDisplayRoomInput);

        RoomEntity roomEntity = roomRepository.getReferenceById(userDisplayRoomInput.getRoomID());
        List<ReservationEntity> reservationEntityList=reservationRepository.findByRoomId(roomEntity.getId());
        List<LocalDate> startDates=reservationEntityList.stream().map(ReservationEntity::getStartDate).toList();
        List<LocalDate> endDates=reservationEntityList.stream().map(ReservationEntity::getEndDate).toList();

        UserDisplayRoomOutput userDisplayRoomOutput = UserDisplayRoomOutput.builder()
                .ID(roomEntity.getId())
                .price(roomEntity.getPrice())
                .floor(roomEntity.getFloor())
                .bedSize(roomEntity.getBedList().stream().map(bed -> Bed.getByCode(bed.getType().toString())).filter(bed1 -> !bed1.equals(Bed.UNKNOWN)).toList())
                .bathRoom(BathRoom.getByCode(roomEntity.getBathTypes().toString()))
                .datesOccupied(List.of(startDates,endDates))
                .build();
        log.info("End display room: {}", userDisplayRoomOutput);
        return userDisplayRoomOutput;

    }

    @Override
    public UserBookOutput bookRoom(UserBookInput userBookInput) {
        //,MOVED
        log.info("Start book room: {}", userBookInput);
        Long year=ChronoUnit.YEARS.between(userBookInput.getDateOfBirth(), LocalDate.now());
        if(year<18L){
            throw new InputException("User is not old enough");
        }
        Long daysAtHotel=ChronoUnit.DAYS.between(userBookInput.getStartDate(), userBookInput.getEndDate());
        if(userRepository.getAllEmails().contains(userBookInput.getEmail())){
            throw new InputException("The following email is taken!");
        }
        List<UUID> roomIDs=reservationRepository.findBetweenStartDateAndEndDate(userBookInput.getStartDate(), userBookInput.getEndDate());
        if(roomIDs.contains(userBookInput.getRoomID())){
            throw new InputException("The following roomID is taken for that period");
        }

       UserEntity userEntity = UserEntity.builder()
                .email(userBookInput.getEmail())
                .firstName(userBookInput.getFirstName())
                .lastName(userBookInput.getLastName())
                .phoneNumber(userBookInput.getPhoneNo())
                .birthday(userBookInput.getDateOfBirth())
                .build();
        userRepository.save(userEntity);
        RoomEntity roomEntity = roomRepository.getReferenceById(userBookInput.getRoomID());


        ReservationEntity reservationEntity = ReservationEntity.builder()
                .room(roomRepository.getReferenceById(userBookInput.getRoomID()))
                .price(BigDecimal.valueOf(daysAtHotel).multiply(roomRepository.getReferenceById(userBookInput.getRoomID()).getPrice()))
                .endDate(userBookInput.getEndDate())
                .startDate(userBookInput.getStartDate())
                .room(roomEntity)
                .user(userEntity)
                .build();

        reservationRepository.save(reservationEntity);
        UserBookOutput userBookOutput = UserBookOutput.builder()
                .message("Successfully booked a room")
                .build();
        log.info("End book room: {}", userBookOutput);
        return userBookOutput;
    }

    @Override
    public UserUnbookOutput unBookRoom(UserUnbookInput unBookInputUser) {
        //MOVED
        log.info("Start unbook room: {}", unBookInputUser);
        reservationRepository.deleteById(unBookInputUser.getBookId());
        UserUnbookOutput userUnbookOutput = UserUnbookOutput.builder()
                .message("Successfully unbooked a room")
                .build();
        log.info("End unbook room: {}", userUnbookOutput);
        return userUnbookOutput;
    }

    @Override
    public UserRegisterOutput registerPerson(UserRegisterInput userRegisterInput) {
        //moved
       log.info("Start register person: {}", userRegisterInput);
       if(roomRepository.findByRoomNumber(userRegisterInput.getRoomNumber()).isEmpty()){
           throw new InputException("Room number is wrong");
       }
       UUID roomID=roomRepository.findByRoomNumber(userRegisterInput.getRoomNumber())
               .get()
               .getId();
       Optional<UUID> reservationIDOptional= reservationRepository.findByRoomIDAndStartDateAndEndDate(roomID.toString(), userRegisterInput.getStartDate(), userRegisterInput.getEndDate());
       if(reservationIDOptional.isEmpty()){
           throw new InputException("Reservation ID is wrong");
       }
       ReservationEntity reservationEntity = reservationRepository.getReferenceById(reservationIDOptional.get());
       List<GuestEntity> guestEntities = userRegisterInput.getUsers().stream().map(e -> GuestEntity.builder()
               .authority(e.getAuthority())
               .birthDate(e.getDateOfBirth())
               .firstName(e.getFirstName())
               .lastName(e.getLastName())
               .validity(e.getValidity())
               .issueDate(e.getIssueDate())
               .idCardNumber(e.getIdNumber())
               .phoneNumber(e.getPhone())
               .build()).toList();
       guestRepository.saveAll(guestEntities);
       reservationEntity.setGuests(guestEntities);
       reservationRepository.flush();
       UserRegisterOutput userRegisterOutput = UserRegisterOutput.builder()
                .message("Successfully registered room")
                .build();
       log.info("End register person: {}", userRegisterOutput);
        return userRegisterOutput;
    }

    @Override
    public AdminRegisterOutput adminRegister(AdminRegisterInput adminInfoInput) {
        //TODO
        //MOVED
        log.info("Start admin info: {}", adminInfoInput);

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
        //moved
        log.info("Start admin create room: {}", adminCreateInput);
        BathRoom bathRoom=BathRoom.getByCode(adminCreateInput.getBathRoom());
        if(bathRoom.equals(BathRoom.UNKNOWN)){
            throw new InputException("Bathroom is unknown");
        }
        Optional<RoomEntity> roomEntityOptional = roomRepository.findByRoomNumber(adminCreateInput.getRoomNumber());
        if(roomEntityOptional.isPresent()){
            throw new InputException("Room already exists");
        }
        List<String> bedStrings = Arrays.stream(Bed.values()).map(Bed::toString).toList();
        List<String> inputBedEntities = adminCreateInput.getBedType();
        if(Collections.disjoint(bedStrings,inputBedEntities)){
            throw new InputException("Given list does not contain any valid bed");
        }
        inputBedEntities.retainAll(bedStrings);
        log.info("input bed entities,{}", inputBedEntities);
        List<BedEntity> entities = inputBedEntities.stream()
                .map(BedTypes::getByCode)
                .filter(bedType -> !bedType.equals(BedTypes.UNKNOWN))
                .map(bedType -> bedRepository.findEntityByType(bedType.name()))
                .toList();

        RoomEntity roomEntity = RoomEntity.builder()
                .roomNumber(adminCreateInput.getRoomNumber())
                .bathTypes(BathTypes.getByCode(adminCreateInput.getBathRoom()))
                .floor(adminCreateInput.getFloor())
                .price(adminCreateInput.getPrice())
                .bedList(entities)
                .build();


        roomRepository.save(roomEntity);
        AdminCreateOutput adminCreateOutput = AdminCreateOutput.builder()
                .ID(roomEntity.getId())
                .roomNumber(roomEntity.getRoomNumber())
                .build();
        log.info("End admin create room: {}", adminCreateOutput);
        return adminCreateOutput;
    }

    @Override
    public AdminUpdateOutput adminUpdate(AdminUpdateInput adminUpdateInput) {
        //moved
        log.info("Start admin update room: {}", adminUpdateInput);
        Optional<RoomEntity> roomEntityOptional = roomRepository.findById(adminUpdateInput.getRoomID());
        if(roomEntityOptional.isEmpty()){
            throw new InputException("Room not found");
        }
        BathRoom bathRoom=BathRoom.getByCode(adminUpdateInput.getBathRoom());
        if(bathRoom.equals(BathRoom.UNKNOWN)){
            throw new InputException("Bathroom is unknown");
        }
        List<String> bedStrings = Arrays.stream(Bed.values()).map(Bed::toString).toList();
        List<String> inputBedEntities = adminUpdateInput.getBedSize();
        if(Collections.disjoint(bedStrings,inputBedEntities)){
            throw new InputException("Given list does not contain any valid bed");
        }
        inputBedEntities.retainAll(bedStrings);
        log.info("input bed entities,{}", inputBedEntities);
        List<BedEntity> entities = inputBedEntities.stream()
                .map(BedTypes::getByCode)
                .filter(bedType -> !bedType.equals(BedTypes.UNKNOWN))
                .map(bedType -> bedRepository.findEntityByType(bedType.name()))
                .toList();

        log.info("Bed entities list{}",entities);
        RoomEntity updateEntity=roomRepository.getReferenceById(adminUpdateInput.getRoomID());
        updateEntity.setBathTypes(BathTypes.getByCode(adminUpdateInput.getBathRoom()));
        updateEntity.setFloor(adminUpdateInput.getFloor());
        updateEntity.setPrice(adminUpdateInput.getPrice());
        updateEntity.setRoomNumber(adminUpdateInput.getRoomNumber());
        updateEntity.setBedList(entities);
        log.info("Roomentity,{}", updateEntity);

        roomRepository.flush();
        AdminUpdateOutput adminUpdateOutput = AdminUpdateOutput.builder()
                .ID(updateEntity.getId())
                .roomNumber(updateEntity.getRoomNumber())
                .build();
        log.info("End admin update room: {}", adminUpdateOutput);
        return adminUpdateOutput;
    }

    @Override
    public AdminPartialUpdateOutput adminPartialUpdate(AdminPartialUpdateInput adminPartialUpdateInput) {
        //todo
        //moved

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
        //moved
        log.info("Start admin delete room: {}", adminDeleteInput);
        Optional<RoomEntity> roomEntityOptional= roomRepository.findById(adminDeleteInput.getID());
        if(roomEntityOptional.isEmpty()){
            throw new InputException("Room not found");
        }
        roomRepository.deleteById(adminDeleteInput.getID());
        AdminDeleteOutput adminDeleteOutput = AdminDeleteOutput.builder()
                .message("Successfully deleted room with ID: "+adminDeleteInput.getID())
                .build();
        log.info("End admin delete room: {}", adminDeleteOutput);
        return adminDeleteOutput;
    }

}
