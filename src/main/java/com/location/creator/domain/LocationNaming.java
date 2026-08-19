package com.location.creator.domain;

import java.util.Objects;

public final class LocationNaming {

    private LocationNaming() {
        throw new AssertionError("No instances!");
    }

    public static String nameFor(LocationTypes type, String code, String roomCode) {
        Objects.requireNonNull(roomCode, "roomCode should not be null");

        return switch (type) {
            case REFRIGERATOR, FREEZER, BENCH -> roomCode + "." + code;
            case BUILDING, ROOM, SHELF, DRAWER, TRAY -> code;
        };

    }
}
