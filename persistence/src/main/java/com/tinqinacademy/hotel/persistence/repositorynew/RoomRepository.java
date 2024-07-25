package com.tinqinacademy.hotel.persistence.repositorynew;

import com.tinqinacademy.hotel.persistence.entities.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, UUID> {
    @Query(value="Select * from rooms where id not in (select room_id from reservations)", nativeQuery = true)
    List<Optional<RoomEntity>> findByCustom();

}
