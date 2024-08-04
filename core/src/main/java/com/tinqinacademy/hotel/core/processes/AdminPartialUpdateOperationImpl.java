package com.tinqinacademy.hotel.core.processes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.tinqinacademy.hotel.api.enums.Bed;
import com.tinqinacademy.hotel.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.hotel.api.exceptions.InputException;
import com.tinqinacademy.hotel.api.exceptions.QueryException;
import com.tinqinacademy.hotel.api.model.operations.admin.partialupdate.AdminPartialUpdateInput;
import com.tinqinacademy.hotel.api.model.operations.admin.partialupdate.AdminPartialUpdateOperation;
import com.tinqinacademy.hotel.api.model.operations.admin.partialupdate.AdminPartialUpdateOutput;
import com.tinqinacademy.hotel.core.families.casehandlers.InputQueryExceptionCase;
import com.tinqinacademy.hotel.core.families.converters.BedEnumToStringListConverter;
import com.tinqinacademy.hotel.core.families.converters.StringInputBedsToBedEntitiesConverter;
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

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AdminPartialUpdateOperationImpl extends BaseProcess implements AdminPartialUpdateOperation {
    private final RoomRepository roomRepository;
    private final ObjectMapper objectMapper;
    private final BedEnumToStringListConverter bedEnumToStringListConverter;
    private final StringInputBedsToBedEntitiesConverter stringInputBedsToBedEntities;
    @Autowired
    public AdminPartialUpdateOperationImpl(ConversionService conversionService, ErrorsProcessor errorMapper, Validator validator, RoomRepository roomRepository, ObjectMapper objectMapper, BedEnumToStringListConverter bedEnumToStringListConverter, StringInputBedsToBedEntitiesConverter stringInputBedsToBedEntities) {
        super(conversionService, errorMapper, validator);
        this.roomRepository = roomRepository;
        this.objectMapper = objectMapper;
        this.bedEnumToStringListConverter = bedEnumToStringListConverter;
        this.stringInputBedsToBedEntities = stringInputBedsToBedEntities;
    }

    @Override
    public Either<ErrorsProcessor, AdminPartialUpdateOutput> process(AdminPartialUpdateInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
            //todo
            log.info("Start admin partial update room: {}", input);
                    Optional<RoomEntity> optionalRoom = roomRepository.findById(input.getRoomID());
                    if(optionalRoom.isEmpty()){
                        throw new QueryException("Room is absent");
                    }
                    BathTypes bathType=BathTypes.getByCode(input.getBathRoom());
                    RoomEntity room = optionalRoom.get();


                    RoomEntity inputPatch = RoomEntity.builder()
                            .id(input.getRoomID())
                            .price(input.getPrice())
                            .bathTypes(bathType)
                            .roomNumber(input.getRoomNumber())
                            .build();
                    if(input.getBedTypes()!=null && !input.getBedTypes().isEmpty()){
                        List<String> bedStrings = bedEnumToStringListConverter.convert(Bed.values());
                        List<String> inputBedEntities=retainLists(input.getBedTypes(),bedStrings);
                        inputPatch.toBuilder().bedList(stringInputBedsToBedEntities.convert(inputBedEntities));
                    }
                    JsonNode roomNode = objectMapper.valueToTree(room);
                    JsonNode inputNode = objectMapper.valueToTree(inputPatch);
                    try {

                        JsonMergePatch patch = JsonMergePatch.fromJson(inputNode);
                        JsonNode patchedNode = patch.apply(roomNode);

                        RoomEntity patchedRoom = objectMapper.treeToValue(patchedNode, RoomEntity.class);
                        roomRepository.saveAndFlush(patchedRoom);

                        AdminPartialUpdateOutput output = AdminPartialUpdateOutput.builder()
                                .ID(patchedRoom.getId().toString())
                                .build();
                        log.info("End adminPartialUpdate output: {}", output);
                        return output;
                    } catch (JsonPatchException | IOException e) {
                        throw new RuntimeException("Failed to apply patch to room: " + e.getMessage(), e);
                    }

        }).toEither()
                .mapLeft(InputQueryExceptionCase::handleThrowable))
                ;
    }
    private List<String> retainLists(List<String> listToRetain,List<String> listToExclude){
        if(Collections.disjoint(listToRetain,listToExclude)){
            throw new InputException("Given list does not contain any valid bed");
        }
        listToRetain.retainAll(listToExclude);
        return listToRetain;
    }

}
