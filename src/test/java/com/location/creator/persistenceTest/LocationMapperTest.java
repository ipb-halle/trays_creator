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
    public void to_entity_checkeEachField(LocationNode node, LocationEntity entity) {
        String ancestorEid = "ancestorEid:12345";
        LocationEntity entityResult = LocationMapper.toEntity(node, ancestorEid);

        assertThat(entityResult).usingRecursiveComparison().ignoringFields("id", "eid").isEqualTo(entity);
    }

    public static Stream<Arguments> parameters_for_mapping() {
        return Stream.of(
                Arguments.of(LocationNode.of(LocationTypes.REFRIGERATOR, "K1", "R302"),
                        LocationEntity.builder()
                                .name("R302.K1")
                                .code("K1")
                                .type(LocationTypes.REFRIGERATOR)
                                .movable(false)
                                .ancestorEid("ancestorEid:12345")
                                .build())
        );
    }
}
