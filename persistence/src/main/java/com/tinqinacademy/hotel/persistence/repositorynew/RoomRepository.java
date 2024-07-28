package com.tinqinacademy.hotel.persistence.repositorynew;

import com.tinqinacademy.hotel.persistence.entities.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, UUID> {
    String querry = """
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

    @Query(value = querry, nativeQuery = true)
    List<UUID> findByCustom(LocalDate endDate, LocalDate startDate, String bathRoom, String bedTypes);


}
