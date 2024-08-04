package com.tinqinacademy.hotel.persistence.repositorynew;

import com.tinqinacademy.hotel.persistence.entities.GuestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface GuestRepository extends JpaRepository<GuestEntity, UUID>, JpaSpecificationExecutor<GuestEntity> {

}
