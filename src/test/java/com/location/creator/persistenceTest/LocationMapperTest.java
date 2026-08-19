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
    @MethodSource("roundtrip_toEntity_toDomain")
    public void to_entity_to_domain_roundtripTest(LocationNode node, String ancestorEid) {
        LocationEntity entity = LocationMapper.toEntity(node, ancestorEid);
        LocationNode domain = LocationMapper.toDomain(entity);

        assertThat(domain).isEqualTo(node);
    }


    public static Stream<Arguments> roundtrip_toEntity_toDomain() {
        return Stream.of(
                Arguments.of(
                        new LocationNode(LocationTypes.ROOM, "R302", "R302"),
                        null
                ),
                Arguments.of(
                        new LocationNode(LocationTypes.REFRIGERATOR, "K1", "R302.K1"),
                        "ancestorRoom:12345"
                ),
                Arguments.of(
                        new LocationNode(LocationTypes.SHELF, "3", "3"),
                        "ancestorFridge:12345"
                )

        );
    }


    @ParameterizedTest
    @MethodSource("to_domain_parameters")
    public void to_domain_checksFields(LocationEntity entity, LocationNode node) {

        LocationNode domain = LocationMapper.toDomain(entity);

        assertThat(domain).usingRecursiveComparison().ignoringFields("id", "eid", "ancestorEid").isEqualTo(node);

    }

    public static Stream<Arguments> to_domain_parameters() {
        return Stream.of(
                Arguments.of(
                        LocationEntity.builder()
                                .name("R302")
                                .code("R302")
                                .type(LocationTypes.ROOM)
                                .movable(false)
                                .ancestorEid(null)
                                .build(),
                        LocationNode.of(LocationTypes.ROOM, "R302", "R302")),
                Arguments.of(
                        LocationEntity.builder()
                                .name("R302.K1")
                                .code("K1")
                                .type(LocationTypes.REFRIGERATOR)
                                .movable(false)
                                .ancestorEid("room:12345")
                                .build(),
                        LocationNode.of(LocationTypes.REFRIGERATOR, "K1", "R302")),
                Arguments.of(
                        LocationEntity.builder()
                                .name("3")
                                .code("3")
                                .type(LocationTypes.SHELF)
                                .movable(true)
                                .ancestorEid("fridge:12345")
                                .build(),
                        LocationNode.of(LocationTypes.SHELF, "3", "R302"))
        );
    }


    @ParameterizedTest
    @MethodSource("parameters_for_mapping_locationNode_to_LocationEntity")
    public void to_entity_checkeEachField(LocationNode node, String ancestorEid, LocationEntity entity) {
        LocationEntity entityResult = LocationMapper.toEntity(node, ancestorEid);

        assertThat(entityResult.getId()).isNull();
        assertThat(entityResult.getEid()).isNull();
        assertThat(entityResult).usingRecursiveComparison().ignoringFields("id", "eid").isEqualTo(entity);
    }

    public static Stream<Arguments> parameters_for_mapping_locationNode_to_LocationEntity() {
        return Stream.of(
                Arguments.of(LocationNode.of(LocationTypes.ROOM, "R302", "R302"), null,
                        LocationEntity.builder()
                                .name("R302")
                                .code("R302")
                                .type(LocationTypes.ROOM)
                                .movable(false)
                                .ancestorEid(null)
                                .build()),
                Arguments.of(LocationNode.of(LocationTypes.REFRIGERATOR, "K1", "R302"), "room:12345",
                        LocationEntity.builder()
                                .name("R302.K1")
                                .code("K1")
                                .type(LocationTypes.REFRIGERATOR)
                                .movable(false)
                                .ancestorEid("room:12345")
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
    }


}
