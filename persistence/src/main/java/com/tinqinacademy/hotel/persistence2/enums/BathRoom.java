package com.tinqinacademy.hotel.persistence2.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum BathRoom {
    PRIVATE("private"), SHARED("shared"),UNKNOWN(null);
    private final String val;
    private static final Map<String, BathRoom> map= new HashMap<>();
    static {
        for (BathRoom bt : BathRoom.values()) {
            map.put(bt.toString(), bt);
        }
    }
    BathRoom(String s) {
        this.val =s;
    }
    @JsonValue
    public String toString(){
        return val;
    }
    @JsonCreator
    public static BathRoom getByCode(String code){
    if(map.containsKey(code)) {
        return map.get(code);
    }
    return BathRoom.UNKNOWN;
    }

}
