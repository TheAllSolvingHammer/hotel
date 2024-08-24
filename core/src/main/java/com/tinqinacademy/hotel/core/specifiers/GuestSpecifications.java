package com.tinqinacademy.hotel.core.specifiers;

import com.tinqinacademy.hotel.persistence.entities.GuestEntity;
import org.springframework.data.jpa.domain.Specification;

public class GuestSpecifications {

    private static boolean isEmpty(String value) {
        return value != null && !value.isEmpty();
    }

    public static Specification<GuestEntity> hasFirstName(final String firstName) {
        return isEmpty(firstName) ? ((guest, query, cb) -> cb.equal(guest.get("first_name"), firstName)) : ((root, query, cb) -> cb.conjunction());
    }

    public static Specification<GuestEntity> hasLastName(final String lastName) {
        return isEmpty(lastName)
                ? (guest, cq, cb) -> cb.equal(guest.get("last_name"), lastName)
                : (root, query, cb) -> cb.conjunction();
    }

    public static Specification<GuestEntity> hasCardNumber(final String cardNumber) {
        return isEmpty(cardNumber)
                ? (guest, cq, cb) -> cb.equal(guest.get("id_card_number"), cardNumber)
                : (root, query, cb) -> cb.conjunction();
    }

    public static Specification<GuestEntity> hasCardIssueAuthority(String cardIssueAuthority) {
        return isEmpty(cardIssueAuthority)
                ? (guest, cq, cb) -> cb.equal(guest.get("authority"), cardIssueAuthority)
                : (root, query, cb) -> cb.conjunction();
    }

    public static Specification<GuestEntity> hasCardValidity(String cardValidity) {
        return isEmpty(cardValidity)
                ? (guest, cq, cb) -> cb.equal(guest.get("validity"), cardValidity)
                : (root, query, cb) -> cb.conjunction();
    }

    public static Specification<GuestEntity> hasCardIssueDate(String cardIssueDate) {
        return isEmpty(cardIssueDate)
                ? (guest, cq, cb) -> cb.equal(guest.get("issue_date"), cardIssueDate)
                : (root, query, cb) -> cb.conjunction();
    }

    public static Specification<GuestEntity> hasBirthDate(String birthDate) {
        return isEmpty(birthDate)
                ? (guest, cq, cb) -> cb.equal(guest.get("birth_date"), birthDate)
                : (root, query, cb) -> cb.conjunction();
    }
}