package com.location.creator.rest;

import org.hibernate.AssertionFailure;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

public final class CreateLocationRequest {

    private CreateLocationRequest() {
        throw new AssertionFailure("no instances allowed!");
    }

    public static JsonNode of(String typeId, String locationName, String ancestorId) {

        return null;
    }

    public static JsonNode toJson(ObjectMapper mapper) {
        return null;
    }
}
