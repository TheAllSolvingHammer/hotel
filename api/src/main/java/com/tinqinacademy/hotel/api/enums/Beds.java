package com.tinqinacademy.hotel.api.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum Beds {
    SINGLE("single"),
    DOUBLE("double"),
    SMALLDOUBLE("smallDouble"),
    KINGSIZE("singlekingsize"),
    QUEENSIZE("doublekingsize"),

    UNKNOWN(null);
    private final String val;

    private static final Map<String, Beds> map= new HashMap<>();
    static {
        for(Beds b : Beds.values()){
            map.put(b.val, b);
        }
    }

    Beds(String name) {
        this.val = name;
    }
    @JsonValue
    public String toString(){
        return val;
    }
    @JsonCreator
    public static Beds getByCode(String code){
    if(map.containsKey(code)) {
        return map.get(code);
    }
    return Beds.UNKNOWN;
    }
}
