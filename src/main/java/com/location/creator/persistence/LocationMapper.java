package com.location.creator.persistence;

import com.location.creator.domain.LocationNode;

public final class LocationMapper {

    private LocationMapper() {
        throw new AssertionError("No instances!");
    }

    public static LocationEntity toEntity(LocationNode node, String ancestorEid) {
        return LocationEntity
                .builder()
                .name(node.name())
                .code(node.code())
                .type(node.type())
                .movable(node.movable())
                .ancestorEid(ancestorEid)
                .build();
    }
}
