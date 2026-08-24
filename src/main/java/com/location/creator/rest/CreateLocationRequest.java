package com.location.creator.rest;

import tools.jackson.databind.ObjectMapper;

public record CreateLocationRequest(
        String typeId,
        String LocationName,
        String ancestorId,
        Integer rows,
        Integer columns
) {
    private static final String INVENTORY_SECURITY_FIELD_ID = "";


    public static CreateLocationRequest of(String typeId, String locationName, String ancestorId) {

        return new CreateLocationRequest(typeId, locationName, ancestorId, null, null);
    }

    public static CreateLocationRequest ofTray(String typeId, String locationName, String ancestorId) {

        return new CreateLocationRequest(typeId, locationName, ancestorId, null, null);
    }

    public CreateLocationRequest toJson(ObjectMapper mapper) {
        return null;
    }
}
