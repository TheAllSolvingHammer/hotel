package com.tinqinacademy.hotel.persistence.initialise;

import com.tinqinacademy.hotel.persistence.entities.BedEntity;
import com.tinqinacademy.hotel.persistence.entities.RoomEntity;
import com.tinqinacademy.hotel.persistence.enums.BathTypes;
import com.tinqinacademy.hotel.persistence.repositorynew.BedRepository;
import com.tinqinacademy.hotel.persistence.repositorynew.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@Order(2)
public class RoomInitializer implements ApplicationRunner {
    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;
    @Autowired
    public RoomInitializer(final RoomRepository roomRepository,final BedRepository bedRepository ) {
        this.roomRepository = roomRepository;
        this.bedRepository = bedRepository;
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Started initializing rooms");
        if(bedRepository.count() == 0) {
            log.info("No beds found. Failed initializing rooms.");
            throw new Exception("Bed table is empty");
        }
        if(roomRepository.count() == 0) {
            List<BedEntity> bedEntities = bedRepository.findAll();
            Set<RoomEntity> roomEntities = new HashSet<>();
            roomEntities.add(RoomEntity.builder()
                    .bathTypes(BathTypes.PRIVATE)
                    .floor(2)
                    .roomNumber("201")
                    .price(BigDecimal.valueOf(398))
                    .bedList(List.of(bedEntities.getFirst(), bedEntities.get(1), bedEntities.getLast()))
                    .build());
            roomEntities.add(RoomEntity.builder()
                    .bathTypes(BathTypes.SHARED)
                    .floor(1)
                    .roomNumber("100A")
                    .price(BigDecimal.valueOf(4))
                    .bedList(List.of(bedEntities.getLast()))
                    .build());
            roomEntities.add(RoomEntity.builder()
                    .bathTypes(BathTypes.PRIVATE)
                    .floor(2)
                    .roomNumber("200")
                    .price(BigDecimal.valueOf(236))
                    .bedList(List.of(bedEntities.getFirst(), bedEntities.get(1), bedEntities.getFirst()))
                    .build());
            roomRepository.saveAll(roomEntities);

        }
        log.info("Finished initializing rooms");
    }
}
