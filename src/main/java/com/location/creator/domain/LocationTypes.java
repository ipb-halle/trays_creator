package com.location.creator.domain;

public enum LocationTypes {
    BUILDING("Building"),
    ROOM("Room"),
    REFRIGERATOR("Refrigerator"),
    FREEZER("Freezer"),
    BENCH("Bench"),
    SHELF("Shelf"),
    DRAWER("Drawer"),
    TRAY("Tray");

    private final String apiName;

    LocationTypes(String apiName) {
        this.apiName = apiName;
    }

    public static LocationTypes fromName(String location) {
        if (location == null) return null;

        for (LocationTypes t : LocationTypes.values()) {
            if (location.equalsIgnoreCase(t.apiName)) return t;
        }
        return null;
    }
}
