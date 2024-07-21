package com.tinqinacademy.hotel.persistence.repository;

import com.tinqinacademy.hotel.persistence.entities.Entity;

import java.util.List;
import java.util.UUID;

public interface BaseRepository<T extends Entity, ID> {
    void save(T entity);
    T findById(UUID id);
    void delete(UUID id);
    List<T> findAll();
    Long count();
}
