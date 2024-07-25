package com.tinqinacademy.hotel.persistence.repositorynew;

import com.tinqinacademy.hotel.persistence.entities.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, UUID> {
    List<ReservationEntity> findByEndDateGreaterThan(LocalDate startDate);
}
