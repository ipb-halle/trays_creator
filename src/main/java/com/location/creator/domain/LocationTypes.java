package com.location.creator.domain;

public enum LocationTypes {
    ROOM("Room"),
    REFRIGERATOR("Refrigerator"),
    FREEZER("Freezer"),
    BENCH("Bench"),
    SHELF("Shelf"),
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
