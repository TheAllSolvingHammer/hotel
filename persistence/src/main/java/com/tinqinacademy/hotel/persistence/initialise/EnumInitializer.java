package com.tinqinacademy.hotel.persistence.initialise;

import com.tinqinacademy.hotel.persistence.entities.BedEntity;
import com.tinqinacademy.hotel.persistence.enums.BedTypes;
import com.tinqinacademy.hotel.persistence.repositorynew.BedRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
@Order(1)

public class EnumInitializer implements ApplicationRunner {

    private final BedRepository repository;

    @Autowired
    public EnumInitializer(BedRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
      log.info("Started Initializing enums");
      Set<BedEntity> bedList=new HashSet<>(repository.findAll());
      log.info("BedList {}",bedList);
      Set<BedEntity> enumList= convertEnum();
      log.info("EnumList {}",enumList);
      for(BedEntity bed:enumList){
          if(!bedList.contains(bed)){
              repository.save(bed);
          }
      }
      log.info("Finished initializing enums {}",bedList);

    }
    private Set<BedEntity> convertEnum(){
        Set<BedEntity> bedList=new HashSet<>();
        for(BedTypes bedTypes : BedTypes.values()){
            if(bedTypes != BedTypes.UNKNOWN) {
                bedList.add(BedEntity.builder()
                        .type(bedTypes)
                        .capacity(bedTypes.getCapacity())
                        .build());
            }
        }
        return bedList;
    }
}
