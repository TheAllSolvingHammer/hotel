package com.tinqinacademy.hotel.persistence.initialize;

import com.tinqinacademy.hotel.persistence.entities.BedEntity;
import com.tinqinacademy.hotel.persistence.enums.Bed;
import com.tinqinacademy.hotel.persistence.repository.BedRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
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
      Set<BedEntity> enumList= convertEnum();
      for(BedEntity bed:enumList){
          if(!bedList.contains(bed)){
              repository.save(bed);
          }
      }
      log.info("Finished initializing enums {}",bedList);

    }
    private Set<BedEntity> convertEnum(){
        Set<BedEntity> bedList=new HashSet<>();
        for(Bed bed:Bed.values()){
            bedList.add(BedEntity.builder()
                    .id(UUID.randomUUID())
                    .type(bed.toString())
                    .capacity(bed.getCapacity())
                    .build());
        }
        return bedList;
    }
}
