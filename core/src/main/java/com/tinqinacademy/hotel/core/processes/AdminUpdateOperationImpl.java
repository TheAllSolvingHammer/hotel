package com.tinqinacademy.hotel.core.processes;

import com.tinqinacademy.hotel.api.base.ErrorsProcessor;
import com.tinqinacademy.hotel.api.enums.BathRoom;
import com.tinqinacademy.hotel.api.enums.Bed;
import com.tinqinacademy.hotel.api.exceptions.InputException;
import com.tinqinacademy.hotel.api.model.operations.admin.update.AdminUpdateInput;
import com.tinqinacademy.hotel.api.model.operations.admin.update.AdminUpdateOperation;
import com.tinqinacademy.hotel.api.model.operations.admin.update.AdminUpdateOutput;
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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class AdminUpdateOperationImpl extends BaseOperation<AdminUpdateOutput,AdminUpdateInput> implements AdminUpdateOperation {
    private final BedRepository bedRepository;
    private final RoomRepository roomRepository;

    @Override
    public Either<ErrorsProcessor, AdminUpdateOutput> process(AdminUpdateInput input) {
        return Try.of(()->{
            log.info("Start admin update room: {}", input);
            Optional<RoomEntity> roomEntityOptional = roomRepository.findById(input.getRoomID());
            if(roomEntityOptional.isEmpty()){
                throw new InputException("Room not found");
            }
            BathRoom bathRoom=BathRoom.getByCode(input.getBathRoom());
            if(bathRoom.equals(BathRoom.UNKNOWN)){
                throw new InputException("Bathroom is unknown");
            }
            List<String> bedStrings = Arrays.stream(Bed.values()).map(Bed::toString).toList();
            List<String> inputBedEntities = input.getBedSize();
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
            RoomEntity updateEntity=roomRepository.getReferenceById(input.getRoomID());
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
                .mapLeft(InputQueryEntityExceptionCase::handleThrowable);

    }
}
