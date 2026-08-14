package com.location.creator.domain;

public enum LocationTypes {
    BUILDING("Building", false),
    ROOM("Room", false),
    REFRIGERATOR("Refrigerator", false),
    FREEZER("Freezer", false),
    BENCH("Bench", false),
    SHELF("Shelf", true),
    DRAWER("Drawer", true),
    TRAY("Tray", true);

    private final String apiName;
    private final boolean movable;

    LocationTypes(String apiName, boolean movable) {
        this.apiName = apiName;
        this.movable = movable;
    }

    public static LocationTypes fromName(String location) {
        if (location == null) return null;

        for (LocationTypes t : LocationTypes.values()) {
            if (location.equalsIgnoreCase(t.apiName)) return t;
        }
        return null;
    }

    public boolean isMovable() {
        return this.movable;
    }
}
