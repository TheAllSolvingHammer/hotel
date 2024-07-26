package com.tinqinacademy.hotel.api.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Bed {
    SINGLE("single"),
    DOUBLE("double"),
    SMALL_DOUBLE("smallDouble"),
    KING_SIZE("kingSize"),
    QUEEN_SIZE("queenSize"),
    @JsonIgnore
    UNKNOWN("");
    private final String val;

    private static final Map<String, Bed> map = new HashMap<>();

    static {
        Arrays.stream(Bed.values())
                .forEach(b -> map.put(b.val, b));
    }

    Bed(String name) {
        this.val = name;
    }

    @JsonValue
    public String toString() {
        return val;
    }

    @JsonCreator
    public static Bed getByCode(String code) {
        if (map.containsKey(code)) {
            return map.get(code);
        }
        return Bed.UNKNOWN;
    }
}
