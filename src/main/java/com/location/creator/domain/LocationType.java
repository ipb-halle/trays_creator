package com.location.creator.domain;

import com.location.creator.persistence.LocationTypeFieldEmbeddable;

import java.util.List;

public record LocationType(
        String eid,
        String name,
        LocationTypes type,
        List<LocationTypeFieldEmbeddable> fields
) {
}
