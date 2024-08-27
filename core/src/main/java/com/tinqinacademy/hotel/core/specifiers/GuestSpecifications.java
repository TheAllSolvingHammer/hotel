package com.tinqinacademy.hotel.core.specifiers;

import com.tinqinacademy.hotel.persistence.entities.GuestEntity;
import org.springframework.data.jpa.domain.Specification;

public class GuestSpecifications {

    public static boolean isValid(String value){
        return value!=null && !value.isEmpty();
    }
    public static Specification<GuestEntity> guestHasFirstName(String firstName){
        return isValid(firstName)
                ? (guest,cq,cb)->cb.equal(guest.get("firstName"),firstName)
                : ((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
    }
    public static Specification<GuestEntity> guestHasLastName(String lastName){
        return isValid(lastName)
                ? (guest,cq,cb)->cb.equal(guest.get("lastName"),lastName)
                : ((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
    }
    public static Specification<GuestEntity> guestHasPhoneNumber(String phoneNumber){
        return isValid(phoneNumber)
                ? (guest,cq,cb) ->cb.equal(guest.get("phoneNumber"),phoneNumber)
                : ((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
    }
    public static Specification<GuestEntity> guestHasIdCardNumber(String idCardNumber) {
        return isValid(idCardNumber)
                ? (guest, cq, cb) -> cb.equal(guest.get("idCardNumber"), idCardNumber)
                : (root, query, cb) -> cb.conjunction();
    }

    public static Specification<GuestEntity> guestHasIdCardValidity(String idCardValidity) {
        return isValid(idCardValidity)
                ? (guest, cq, cb) -> cb.equal(guest.get("idCardValidity"), idCardValidity)
                : (root, query, cb) -> cb.conjunction();
    }

    public static Specification<GuestEntity> guestHasIdCardIssueAuthority(String idCardIssueAuthority) {
        return isValid(idCardIssueAuthority)
                ? (guest, cq, cb) -> cb.equal(guest.get("idCardIssueAuthority"), idCardIssueAuthority)
                : (root, query, cb) -> cb.conjunction();
    }

    public static Specification<GuestEntity> guestHasIdCardIssueDate(String idCardIssueDate) {
        return isValid(idCardIssueDate)
                ? (guest, cq, cb) -> cb.equal(guest.get("idCardIssueDate"), idCardIssueDate)
                : (root, query, cb) -> cb.conjunction();
    }
//    public static Specification<GuestEntity> guestHasBirthdate(String birthdate) {
//        return isValid(birthdate)
//                ? (guest, cq, cb) -> cb.equal(guest.get("idCardIssueDate"), birthdate)
//                : (root, query, cb) -> cb.conjunction();
//    }
}