package com.tinqinacademy.hotel.persistence.repositorynew;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GuestRepository extends JpaRepository<GuestRepository, UUID> {
}
