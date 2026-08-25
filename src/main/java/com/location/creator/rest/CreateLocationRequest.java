package com.location.creator.rest;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;

public record CreateLocationRequest(
        String typeId,
        String name,
        String ancestorId,
        Integer rows,
        Integer columns
) {
    private static final String INVENTORY_SECURITY_FIELD_ID = "242870a3-dc72-4c84-82e7-6ab8cfc854e7";


    public static CreateLocationRequest of(String typeId, String locationName, String ancestorId) {

        return new CreateLocationRequest(typeId, locationName, ancestorId, null, null);
    }

    public static CreateLocationRequest ofTray(String typeId, String locationName, String ancestorId, int rows, int columns) {

        return new CreateLocationRequest(typeId, locationName, ancestorId, rows, columns);
    }

    public ObjectNode toJson(ObjectMapper mapper) {
        ObjectNode root = mapper.createObjectNode();
        ObjectNode data = root.putObject("data");
        data.put("type", "inventoryLocation");
        ObjectNode attributes = data.putObject("attributes");
        attributes.put("typeId", typeId);
        attributes.put("name", name);
        ArrayNode ancestors = attributes.putArray("ancestors");
        if (ancestorId != null && !ancestorId.isBlank()) {
            ancestors.addObject().put("id", Eids.toUuid(ancestorId));
            ArrayNode fields = attributes.putArray("fields");
            if (rows == null) {
                ObjectNode jsonNodes = fields.addObject();
                jsonNodes.put("id", INVENTORY_SECURITY_FIELD_ID);
                jsonNodes.putObject("content").put("value", "Default");
            } else {
                attributes.put("isGrid", true).put("rows", rows).put("columns", columns);
            }
        }
        return root;
    }
}
