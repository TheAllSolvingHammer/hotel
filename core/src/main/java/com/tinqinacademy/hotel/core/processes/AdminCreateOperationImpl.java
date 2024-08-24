package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.enums.BathRoom;
import com.tinqinacademy.hotel.api.enums.Bed;
import com.tinqinacademy.hotel.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.hotel.api.exceptions.InputException;
import com.tinqinacademy.hotel.api.exceptions.QueryException;
import com.tinqinacademy.hotel.api.model.operations.admin.create.AdminCreateInput;
import com.tinqinacademy.hotel.api.model.operations.admin.create.AdminCreateOperation;
import com.tinqinacademy.hotel.api.model.operations.admin.create.AdminCreateOutput;
import com.tinqinacademy.hotel.core.families.casehandlers.InputQueryEntityExceptionCase;
import com.tinqinacademy.hotel.core.families.converters.BedEnumToStringListConverter;
import com.tinqinacademy.hotel.core.families.converters.StringInputBedsToBedEntitiesConverter;
import com.tinqinacademy.hotel.persistence.entities.BedEntity;
import com.tinqinacademy.hotel.persistence.entities.RoomEntity;
import com.tinqinacademy.hotel.persistence.enums.BathTypes;
import com.tinqinacademy.hotel.persistence.repositorynew.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AdminCreateOperationImpl extends BaseProcess implements AdminCreateOperation {
    private final RoomRepository roomRepository;
    private final BedEnumToStringListConverter bedEnumToStringListConverter;
    private final StringInputBedsToBedEntitiesConverter stringInputBedsToBedEntities;

    @Autowired
    public AdminCreateOperationImpl(ConversionService conversionService, ErrorsProcessor errorMapper, Validator validator, RoomRepository roomRepository, BedEnumToStringListConverter bedEnumToStringListConverter, StringInputBedsToBedEntitiesConverter stringInputBedsToBedEntities) {
        super(conversionService, errorMapper, validator);
        this.roomRepository = roomRepository;
        this.bedEnumToStringListConverter = bedEnumToStringListConverter;
        this.stringInputBedsToBedEntities = stringInputBedsToBedEntities;
    }

    @Override
    public Either<ErrorsProcessor, AdminCreateOutput> process(AdminCreateInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    log.info("Start admin create room: {}", input);
                    getBathRoomCheck(input.getBathRoom());
                    checkRoom(input.getRoomNumber());
                    List<String> bedStrings = bedEnumToStringListConverter.convert(Bed.values());
                    List<String> inputBedEntities=retainLists(input.getBedType(),bedStrings);
                    List<BedEntity> entities = stringInputBedsToBedEntities.convert(inputBedEntities);
                    RoomEntity roomEntity = RoomEntity.builder()
                            .roomNumber(input.getRoomNumber())
                            .bathTypes(BathTypes.getByCode(input.getBathRoom()))
                            .floor(input.getFloor())
                            .price(input.getPrice())
                            .bedList(entities)
                            .build();
                    roomRepository.save(roomEntity);
                    AdminCreateOutput adminCreateOutput = AdminCreateOutput.builder()
                            .ID(roomEntity.getId())
                            .roomNumber(roomEntity.getRoomNumber())
                            .build();
                    log.info("End admin create room: {}", adminCreateOutput);
                    return adminCreateOutput;
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
    private void checkRoom(String input) {
        Optional<RoomEntity> roomEntityOptional = roomRepository.findByRoomNumber(input);
        if(roomEntityOptional.isPresent()){
            throw new QueryException("Room already exists");
        }
    }

    private void getBathRoomCheck(String input){
        BathRoom bathRoom=BathRoom.getByCode(input);
        if(bathRoom.equals(BathRoom.UNKNOWN)){
            throw new InputException("Bathroom is unknown");
        }
    }


}
