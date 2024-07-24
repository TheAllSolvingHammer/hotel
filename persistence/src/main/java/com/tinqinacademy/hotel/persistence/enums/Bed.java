package com.tinqinacademy.hotel.persistence.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum Bed {
    SINGLE("single",1),
    DOUBLE("double",2),
    SMALLDOUBLE("smalldouble",2),
    KINGSIZE("kingsize",3),
    QUEENSIZE("queensize",3),

    UNKNOWN(null,0);
    private final String val;
    @Getter
    private final Integer capacity;

    private static final Map<String, Bed> map= new HashMap<>();
    static {
        for(Bed b : Bed.values()){
            map.put(b.val, b);
        }
    }

    Bed(String name,Integer capacity) {
        this.val = name;
        this.capacity = capacity;
    }
    @JsonValue
    public String toString(){
        return val;
    }
    @JsonCreator
    public static Bed getByCode(String code){
    if(map.containsKey(code)) {
        return map.get(code);
    }
    return Bed.UNKNOWN;
    }

    public static int lengthOfBed(){
       return Bed.values().length;
    }

}
