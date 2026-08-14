package com.location.creator.domainTest;

import com.location.creator.domain.LocationNaming;
import com.location.creator.domain.LocationTypes;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LocationNamingTest {


    @ParameterizedTest
    @MethodSource("nameSources")
    public void nameFor_setCorrectNameForDevice(LocationTypes type, String roomNumber, String code, String expectedName) {
        String s = LocationNaming.nameFor(type, roomNumber, code);
        assertThat(s).isEqualTo(expectedName);
    }

    public static Stream<Arguments> nameSources() {
        return Stream.of(
                Arguments.of(LocationTypes.ROOM, "R2-208", "R2-208", "R2-208"),
                Arguments.of(LocationTypes.REFRIGERATOR, "R2-208", "K1", "R2-208.K1"),
                Arguments.of(LocationTypes.FREEZER, "R2-304", "G1", "R2-304.G1"),
                Arguments.of(LocationTypes.BENCH, "R2-207", "P1", "R2-207.P1"),
                Arguments.of(LocationTypes.SHELF, "R2-208", "3", "3"),
                Arguments.of(LocationTypes.DRAWER, "R2-304", "5", "5"),
                Arguments.of(LocationTypes.REFRIGERATOR, "R003", "K5", "R003.K5"),
                Arguments.of(LocationTypes.FREEZER, "R002", "G11", "R002.G11")
        );

    }
}
