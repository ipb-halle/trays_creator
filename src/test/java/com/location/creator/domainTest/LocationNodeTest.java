package com.location.creator.domainTest;

import com.location.creator.domain.LocationNode;
import com.location.creator.domain.LocationTypes;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LocationNodeTest {

    @ParameterizedTest
    @MethodSource("locationTypes")
    public void isMovable_checkTypeIsMovableTrueFalse(LocationTypes type, boolean movable) {
        LocationNode ln = new LocationNode(type, null, null);
        boolean mv = ln.movable();
        assertThat(mv).isEqualTo(movable);
    }


    private static Stream<Arguments> locationTypes() {
        return Stream.of(
                Arguments.of(LocationTypes.BUILDING, false),
                Arguments.of(LocationTypes.ROOM, false),
                Arguments.of(LocationTypes.REFRIGERATOR, false),
                Arguments.of(LocationTypes.FREEZER, false),
                Arguments.of(LocationTypes.BENCH, false),
                Arguments.of(LocationTypes.SHELF, true),
                Arguments.of(LocationTypes.DRAWER, true),
                Arguments.of(LocationTypes.TRAY, true)

        );
    }
}
