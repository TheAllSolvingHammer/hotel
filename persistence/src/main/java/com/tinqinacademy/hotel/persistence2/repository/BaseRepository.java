package com.tinqinacademy.hotel.persistence2.repository;

import java.util.List;

public interface BaseRepository<T, UUID> {
    void save(T entity);
    T findById(UUID id);
    void delete(UUID id);
    List<T> findAll();
    Long count();
}
