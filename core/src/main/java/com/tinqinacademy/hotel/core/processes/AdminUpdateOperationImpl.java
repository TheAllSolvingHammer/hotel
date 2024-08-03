package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.hotel.api.enums.BathRoom;
import com.tinqinacademy.hotel.api.enums.Bed;
import com.tinqinacademy.hotel.api.exceptions.InputException;
import com.tinqinacademy.hotel.api.model.operations.admin.update.AdminUpdateInput;
import com.tinqinacademy.hotel.api.model.operations.admin.update.AdminUpdateOperation;
import com.tinqinacademy.hotel.api.model.operations.admin.update.AdminUpdateOutput;
import com.tinqinacademy.hotel.core.families.casehandlers.InputQueryEntityExceptionCase;
import com.tinqinacademy.hotel.core.families.converters.BedEnumToStringListConverter;
import com.tinqinacademy.hotel.core.families.converters.StringInputBedsToBedEntitiesConverter;
import com.tinqinacademy.hotel.persistence.entities.BedEntity;
import com.tinqinacademy.hotel.persistence.entities.RoomEntity;
import com.tinqinacademy.hotel.persistence.enums.BathTypes;
import com.tinqinacademy.hotel.persistence.enums.BedTypes;
import com.tinqinacademy.hotel.persistence.repositorynew.BedRepository;
import com.tinqinacademy.hotel.persistence.repositorynew.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class AdminUpdateOperationImpl extends BaseProcess implements AdminUpdateOperation{
    private final BedRepository bedRepository;
    private final RoomRepository roomRepository;
    private final BedEnumToStringListConverter bedEnumToStringListConverter;
    private final StringInputBedsToBedEntitiesConverter stringInputBedsToBedEntities;
    @Autowired
    public AdminUpdateOperationImpl(ConversionService conversionService, ErrorsProcessor errorMapper, Validator validator, BedRepository bedRepository, RoomRepository roomRepository, BedEnumToStringListConverter bedEnumToStringListConverter, StringInputBedsToBedEntitiesConverter stringInputBedsToBedEntities) {
        super(conversionService, errorMapper, validator);
        this.bedRepository = bedRepository;
        this.roomRepository = roomRepository;
        this.bedEnumToStringListConverter = bedEnumToStringListConverter;
        this.stringInputBedsToBedEntities = stringInputBedsToBedEntities;
    }


    @Override
    public Either<ErrorsProcessor, AdminUpdateOutput> process(@Valid AdminUpdateInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(() -> {
                    log.info("Start admin update room: {}", input);
                    verifyRoom(input);
                    verifyBathroom(input);
                    List<String> bedStrings = bedEnumToStringListConverter.convert(Bed.values());
                    List<String> inputBedEntities = retainLists(input.getBedSize(), bedStrings);
                    List<BedEntity> entities = stringInputBedsToBedEntities.convert(inputBedEntities);


                    RoomEntity updateEntity = roomRepository.getReferenceById(input.getRoomID());
                    updateEntity.setBathTypes(BathTypes.getByCode(input.getBathRoom()));
                    updateEntity.setFloor(input.getFloor());
                    updateEntity.setPrice(input.getPrice());
                    updateEntity.setRoomNumber(input.getRoomNumber());
                    updateEntity.setBedList(entities);
                    log.info("Roomentity,{}", updateEntity);

                    roomRepository.flush();
                    AdminUpdateOutput adminUpdateOutput = AdminUpdateOutput.builder()
                            .ID(updateEntity.getId())
                            .roomNumber(updateEntity.getRoomNumber())
                            .build();
                    log.info("End admin update room: {}", adminUpdateOutput);
                    return adminUpdateOutput;
                }).toEither()
                .mapLeft(InputQueryEntityExceptionCase::handleThrowable));

    }
    private List<String> retainLists(List<String> listToRetain,List<String> listToExclude){
        if(Collections.disjoint(listToRetain,listToExclude)){
            throw new InputException("Given list does not contain any valid bed");
        }
        listToRetain.retainAll(listToExclude);
        return listToRetain;
    }

    private static void verifyBathroom(AdminUpdateInput input) {
        BathRoom bathRoom=BathRoom.getByCode(input.getBathRoom());
        if(bathRoom.equals(BathRoom.UNKNOWN)){
            throw new InputException("Bathroom is unknown");
        }
    }

    private void verifyRoom(AdminUpdateInput input) {
        Optional<RoomEntity> roomEntityOptional = roomRepository.findById(input.getRoomID());
        if(roomEntityOptional.isEmpty()){
            throw new InputException("Room not found");
        }
    }
}
