package com.tinqinacademy.hotel.persistence2.repositorynew;

import com.tinqinacademy.hotel.persistence2.entities.BedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BedRepository extends JpaRepository<BedEntity, UUID> {
}
