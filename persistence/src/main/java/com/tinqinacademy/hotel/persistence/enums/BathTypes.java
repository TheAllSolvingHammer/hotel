package com.tinqinacademy.hotel.persistence.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum BathTypes {
    PRIVATE("private"), SHARED("shared"),UNKNOWN(null);
    private final String val;
    private static final Map<String, BathTypes> map= new HashMap<>();
    static {
        for (BathTypes bt : BathTypes.values()) {
            map.put(bt.toString(), bt);
        }
    }
    BathTypes(String s) {
        this.val =s;
    }
    @JsonValue
    public String toString(){
        return val;
    }
    @JsonCreator
    public static BathTypes getByCode(String code){
    if(map.containsKey(code)) {
        return map.get(code);
    }
    return BathTypes.UNKNOWN;
    }

}
