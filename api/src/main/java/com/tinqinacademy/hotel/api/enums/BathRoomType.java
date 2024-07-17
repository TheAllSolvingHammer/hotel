package com.tinqinacademy.hotel.api.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum BathRoomType {
    PRIVATE("private"), SHARED("shared"),UNKNOWN(null);
    private final String val;
    private static final Map<String, BathRoomType> map= new HashMap<>();
    static {
        for (BathRoomType bt : BathRoomType.values()) {
            map.put(bt.toString(), bt);
        }
    }
    BathRoomType(String s) {
        this.val =s;
    }
    @JsonValue
    public String toString(){
        return val;
    }
    @JsonCreator
    public static BathRoomType getByCode(String code){
    if(map.containsKey(code)) {
        return map.get(code);
    }
    return BathRoomType.UNKNOWN;
    }

}
