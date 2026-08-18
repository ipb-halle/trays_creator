package com.location.creator.persistenceTest;

import com.location.creator.domain.LocationNode;
import com.location.creator.domain.LocationTypes;
import com.location.creator.persistence.LocationEntity;
import com.location.creator.persistence.LocationMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class LocationMapperTest {

    @ParameterizedTest
    @MethodSource("parameters_for_mapping")
    public void to_entity_checkeEachField(LocationNode node,String ancestorEid, LocationEntity entity) {
        LocationEntity entityResult = LocationMapper.toEntity(node, ancestorEid);

        assertThat(entityResult.getId()).isNull();
        assertThat(entityResult.getEid()).isNull();
        assertThat(entityResult).usingRecursiveComparison().ignoringFields("id", "eid").isEqualTo(entity);
    }

    public static Stream<Arguments> parameters_for_mapping() {
        Stream<Arguments> argumentsStream = Stream.of(
                Arguments.of(LocationNode.of(LocationTypes.REFRIGERATOR, "K1", "R302"), "room:12345",
                        LocationEntity.builder()
                                .name("R302.K1")
                                .code("K1")
                                .type(LocationTypes.REFRIGERATOR)
                                .movable(false)
                                .ancestorEid("room:12345")
                                .build()),
                Arguments.of(LocationNode.of(LocationTypes.ROOM, "R302", "R302"), null,
                        LocationEntity.builder()
                                .name("R302")
                                .code("R302")
                                .type(LocationTypes.ROOM)
                                .movable(false)
                                .ancestorEid(null)
                                .build()),
                Arguments.of(LocationNode.of(LocationTypes.SHELF, "3", "R302"), "fridge:12345",
                        LocationEntity.builder()
                                .name("3")
                                .code("3")
                                .type(LocationTypes.SHELF)
                                .movable(true)
                                .ancestorEid("fridge:12345")
                                .build())

        );
        return argumentsStream;
    }
}
