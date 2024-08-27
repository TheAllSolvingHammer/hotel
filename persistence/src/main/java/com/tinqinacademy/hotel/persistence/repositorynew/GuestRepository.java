package com.tinqinacademy.hotel.persistence.repositorynew;

import com.tinqinacademy.hotel.persistence.entities.GuestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Repository
public interface GuestRepository extends JpaRepository<GuestEntity, UUID>, JpaSpecificationExecutor<GuestEntity> {

   String query= """
   SELECT g.*
FROM guests g
JOIN reservation_guests rg ON g.guest_id = rg.guest_id
JOIN reservations res ON rg.reservation_id = res.reservation_id
JOIN rooms r ON res.room_id = r.id
            WHERE r.room_number = :roomNumber
                  AND res.start_date <= :endDate
                  AND res.end_date >= :startDate
           """;

    @Query(value = query, nativeQuery = true)
    List<GuestEntity> findByStartDateAndEndDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,@Param("roomNumber") String roomNumber);


}
