package com.tinqinacademy.hotel.persistence.repositorynew;

import com.tinqinacademy.hotel.persistence.entities.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, UUID> {
    String query = """
            SELECT DISTINCT r.room_id
            FROM rooms r
                     RIGHT JOIN room_bed rb ON r.room_id = rb.room_id
                     INNER JOIN beds b ON rb.bed_id = b.bed_id
            WHERE r.room_id NOT IN (
                SELECT res.room_room_id
                FROM reservations res
                WHERE res.start_date <= :endDate
                  AND res.end_date >= :startDate
            )
              AND r.bath_room = :bathRoom
              AND b.type = :bedTypes;
            """;
    @Query(value = query, nativeQuery = true)
    List<UUID> findByCustom(LocalDate endDate, LocalDate startDate, String bathRoom, String bedTypes);

    String query_room= """
            SELECT r.room_id,r.bath_room,r.floor,r.price,r.room_number
            From rooms r
            where r.room_number = :roomNumber
            """;
    @Query(value = query_room, nativeQuery = true)
    Optional<RoomEntity> findByRoomNumber(String roomNumber);


    String query_id= """
            SELECT r.room_id,r.bath_room,r.floor,r.price,r.room_number
            FROM rooms r
            
            WHERE r.room_id=:roomID
            """;
    @Query(value=query_id, nativeQuery=true)
    Optional<RoomEntity> findRoomEntityByID(String roomID);



}
