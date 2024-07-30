package com.tinqinacademy.hotel.persistence.repositorynew;

import com.tinqinacademy.hotel.persistence.entities.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, UUID> {
    List<ReservationEntity> findByEndDateGreaterThan(LocalDate startDate);
   // List<RoomEntity> findByDateBetweenStartDateAndEndDate(LocalDate startDate, LocalDate endDate);

    String query= """
            SELECT r.room_id
            FROM rooms r
            WHERE r.room_id IN (
                SELECT res.room_room_id
                FROM reservations res
                WHERE res.start_date < :endDate AND res.end_date > :startDate
            )
            """;
    @Query(value = query, nativeQuery = true)
    List<UUID> findBetweenStartDateAndEndDate(LocalDate startDate, LocalDate endDate);

    String query_for_dates= """
            SELECT rs
            from ReservationEntity rs
            where rs.room_room_id=:roomID
            """;
    List<ReservationEntity> findByRoomId(UUID roomID);


    String query_for_client= """
            SELECT rs.reservation_id
            from Reservations rs
            where start_date=:startDate
              and end_date=:endDate
              and rs.room_room_id=:roomID
            """;
    @Query(value = query_for_client, nativeQuery = true)
    Optional<UUID> findByRoomIDAndStartDateAndEndDate(String roomID, LocalDate startDate, LocalDate endDate);
}
