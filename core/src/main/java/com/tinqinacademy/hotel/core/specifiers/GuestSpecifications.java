package com.tinqinacademy.hotel.core.specifiers;

import com.tinqinacademy.hotel.persistence.entities.GuestEntity;
import com.tinqinacademy.hotel.persistence.entities.ReservationEntity;
import com.tinqinacademy.hotel.persistence.entities.RoomEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.UUID;

public class GuestSpecifications {
    public static Specification<GuestEntity> hasRoomId(String roomId) {
        return (root, query, cb) -> {
            Join<GuestEntity, ReservationEntity> reservationJoin = root.join("reservations", JoinType.INNER);
            Join<ReservationEntity, RoomEntity> roomJoin = reservationJoin.join("room", JoinType.INNER);
            return cb.equal(roomJoin.get("id"), UUID.fromString(roomId));
        };
    }

    public static Specification<GuestEntity> betweenDates(LocalDate startDate, LocalDate endDate) {
        return (root, query, cb) -> {
            Join<GuestEntity, ReservationEntity> reservationJoin = root.join("reservations", JoinType.INNER);
            return cb.between(reservationJoin.get("startDate"), startDate, endDate);
        };
    }

    public static Specification<GuestEntity> hasFirstName(String firstName) {
        return (root, query, cb) -> cb.equal(root.get("firstName"), firstName);
    }

    public static Specification<GuestEntity> hasLastName(String lastName) {
        return (root, query, cb) -> cb.equal(root.get("lastName"), lastName);
    }

    public static Specification<GuestEntity> hasPhone(String phone) {
        return (root, query, cb) -> cb.equal(root.get("phoneNumber"), phone);
    }

    public static Specification<GuestEntity> hasIdNumber(String idNumber) {
        return (root, query, cb) -> cb.equal(root.get("idCardNumber"), idNumber);
    }
}
