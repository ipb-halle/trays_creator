package com.location.creator.rest;

import com.location.creator.domain.LocationType;
import com.location.creator.domain.LocationTypeField;
import com.location.creator.domain.LocationTypes;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

@Component
public final class LocationTypeParser {

    public List<LocationType> parseData(JsonNode data) {
        List<LocationType> types = new ArrayList<>();
        if (!data.isMissingNode() && data.isArray()) {
            for (JsonNode node : data) {
                JsonNode attributes = node.path("attributes");
                String eid = attributes.path("id").asString();
                String name = attributes.path("name").asString();
                LocationTypes type = LocationTypes.fromName(name);
                List<LocationTypeField> fields = new ArrayList<>();
                JsonNode fieldsArrayNode = attributes.path("fields");
                if (!fieldsArrayNode.isMissingNode() && fieldsArrayNode.isArray()) {
                    for (JsonNode node1 : fieldsArrayNode) {
                        String fieldId = node1.path("id").asString();
                        String title = node1.path("definition").path("title").asString();
                        boolean required = false;
                        if (!node1.path("definition").path("isRequired").isMissingNode()) {
                            required = node1.path("definition").path("isRequired").asBoolean();
                        }
                        LocationTypeField field = new LocationTypeField(fieldId, title, required);
                        fields.add(field);
                    }
                }
                LocationType locationType = new LocationType(eid, name, type, fields);
                types.add(locationType);
            }
        }
        return types;
    }
}
