package com.tinqinacademy.hotel.persistence2.repositorynew;

import com.tinqinacademy.hotel.persistence2.entities.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface RoomRepository extends JpaRepository<RoomEntity, UUID> {
    RoomEntity findByName(String name);
    List<RoomEntity> findAllByOrderByNameAsc();
    RoomEntity findByID(UUID id);
    void deleteByID(UUID id);
    Long countByName(String name);
    Long countAllBy();

}
