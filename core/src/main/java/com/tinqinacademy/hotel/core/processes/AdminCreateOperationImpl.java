package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.enums.BathRoom;
import com.tinqinacademy.hotel.api.enums.Bed;
import com.tinqinacademy.hotel.api.exceptions.InputException;
import com.tinqinacademy.hotel.api.model.operations.admin.create.AdminCreateInput;
import com.tinqinacademy.hotel.api.model.operations.admin.create.AdminCreateOperation;
import com.tinqinacademy.hotel.api.model.operations.admin.create.AdminCreateOutput;
import com.tinqinacademy.hotel.core.exceptionfamilies.InputQueryEntityExceptionCase;
import com.tinqinacademy.hotel.persistence.entities.BedEntity;
import com.tinqinacademy.hotel.persistence.entities.RoomEntity;
import com.tinqinacademy.hotel.persistence.enums.BathTypes;
import com.tinqinacademy.hotel.persistence.enums.BedTypes;
import com.tinqinacademy.hotel.persistence.repositorynew.BedRepository;
import com.tinqinacademy.hotel.persistence.repositorynew.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminCreateOperationImpl extends BaseOperation<AdminCreateOutput,AdminCreateInput> implements AdminCreateOperation {
    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;
    @Override
    public Either<ErrorsProcessor, AdminCreateOutput> process(AdminCreateInput input) {
        return Try.of(()->{
                    log.info("Start admin create room: {}", input);
                    BathRoom bathRoom=BathRoom.getByCode(input.getBathRoom());
                    if(bathRoom.equals(BathRoom.UNKNOWN)){
                        throw new InputException("Bathroom is unknown");
                    }
                    Optional<RoomEntity> roomEntityOptional = roomRepository.findByRoomNumber(input.getRoomNumber());
                    if(roomEntityOptional.isPresent()){
                        throw new InputException("Room already exists");
                    }
                    List<String> bedStrings = Arrays.stream(Bed.values()).map(Bed::toString).toList();
                    List<String> inputBedEntities = input.getBedType();
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
                .mapLeft(InputQueryEntityExceptionCase::handleThrowable);
    }
}
