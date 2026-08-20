package com.location.creator.rest;

public final class Eids {

    private Eids() {
        throw new AssertionError("no instances are allowed!");
    }

    public static String toUuid(String eid) {
        return eid.trim()
                .replace("location:", "")
                .replace(":ivt", "");
    }
}
