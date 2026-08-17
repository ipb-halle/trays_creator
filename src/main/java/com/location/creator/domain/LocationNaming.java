package com.location.creator.domain;

public final class LocationNaming {

    private LocationNaming() {
    }

    public static String nameFor(LocationTypes type, String code, String roomCode) {

        return switch (type) {
            case REFRIGERATOR, FREEZER, BENCH -> roomCode + "." + code;
            case BUILDING, ROOM, SHELF, DRAWER, TRAY -> code;
        };

    }
}
