package com.location.creator.domain;

public record LocationNode(
        LocationTypes type,
        String code,
        String name
) {
    public boolean movable() {
        return type.isMovable();
    }

    public static LocationNode of(LocationTypes type, String code, String roomCode) {
        return new LocationNode(type, code, LocationNaming.nameFor(type, roomCode, code));
    }
}
