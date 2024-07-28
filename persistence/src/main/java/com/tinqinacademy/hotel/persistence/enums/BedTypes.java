package com.tinqinacademy.hotel.persistence.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum BedTypes {
    SINGLE("single",1),
    DOUBLE("double",2),
    SMALLDOUBLE("smallDouble",2),
    KINGSIZE("kingSize",3),
    QUEENSIZE("queenSize",3),

    UNKNOWN(null,0);
    private final String val;
    @Getter
    private final Integer capacity;

    private static final Map<String, BedTypes> map= new HashMap<>();
    static {
        for(BedTypes b : BedTypes.values()){
            map.put(b.val, b);
        }
    }

    BedTypes(String name, Integer capacity) {
        this.val = name;
        this.capacity = capacity;
    }
    @JsonValue
    public String toString(){
        return val;
    }
    @JsonCreator
    public static BedTypes getByCode(String code){
    if(map.containsKey(code)) {
        return map.get(code);
    }
    return BedTypes.UNKNOWN;
    }

    public static int lengthOfBed(){
       return BedTypes.values().length;
    }

}
