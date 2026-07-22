package com.location.creator.domain;

import java.util.List;

public record LocationType(
        String eid,
        String name,
        LocationTypes type,
        List<LocationTypeField> fields
) {
}
