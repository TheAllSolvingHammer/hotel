package com.tinqinacademy.hotel.rest.constants;

public class MappingsConstants {
    public static final String userAvailability="hotel/available";
    public static final String userDisplay="hotel/{roomID}";
    public static final String userBook ="hotel/{roomID}";
    public static final String userUnBook ="hotel/{reservationID}";
    public static final String userRegister ="hotel/register";
    public static final String adminRegister ="system/register";
    public static final String adminCreate ="system/room";
    public static final String adminUpdate ="/system/room/{roomID}";
    public static final String adminPartialUpdate ="/system/room/{roomID}";
    public static final String adminDelete ="system/room/{roomID}";

}
