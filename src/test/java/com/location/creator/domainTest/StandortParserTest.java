package com.location.creator.domainTest;

import com.location.creator.domain.LocationNode;
import com.location.creator.domain.LocationTypes;
import com.location.creator.domain.StandortParser;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class StandortParserTest {

    @ParameterizedTest
    @MethodSource("resolvedCases")
    public void parsePath_resolvesValidStandort(String path, List<LocationNode> expectedNodes) {
        List<LocationNode> locationNodes = StandortParser.parsePath(path);
        assertThat(locationNodes).containsExactlyElementsOf(expectedNodes);
    }

    public static Stream<Arguments> resolvedCases() {
        return Stream.of(
                Arguments.of("R2-208K1-3", List.of(
                        new LocationNode(LocationTypes.BUILDING, "R"),
                        new LocationNode(LocationTypes.ROOM, "R2-208"),
                        new LocationNode(LocationTypes.REFRIGERATOR, "K1"),
                        new LocationNode(LocationTypes.SHELF, "3"))),
                Arguments.of("R2-304G1-5", List.of(
                        new LocationNode(LocationTypes.BUILDING, "R"),
                        new LocationNode(LocationTypes.ROOM, "R2-304"),
                        new LocationNode(LocationTypes.FREEZER, "G1"),
                        new LocationNode(LocationTypes.DRAWER, "5"))),
                Arguments.of("R003.K3", List.of(
                        new LocationNode(LocationTypes.BUILDING, "R"),
                        new LocationNode(LocationTypes.ROOM, "R003"),
                        new LocationNode(LocationTypes.REFRIGERATOR, "K3"))),
                Arguments.of("R2-109P1", List.of(
                        new LocationNode(LocationTypes.BUILDING, "R"),
                        new LocationNode(LocationTypes.ROOM, "R2-109"),
                        new LocationNode(LocationTypes.BENCH, "P1"))),
                Arguments.of("R.104.G.1.1", List.of(
                        new LocationNode(LocationTypes.BUILDING, "R"),
                        new LocationNode(LocationTypes.ROOM, "R104"),
                        new LocationNode(LocationTypes.FREEZER, "G1"),
                        new LocationNode(LocationTypes.DRAWER, "1"))),
                Arguments.of("R002.G11.2", List.of(
                        new LocationNode(LocationTypes.BUILDING, "R"),
                        new LocationNode(LocationTypes.ROOM, "R002"),
                        new LocationNode(LocationTypes.FREEZER, "G11"),
                        new LocationNode(LocationTypes.DRAWER, "2"))),
                Arguments.of("r2-208k1-3", List.of(
                        new LocationNode(LocationTypes.BUILDING, "R"),
                        new LocationNode(LocationTypes.ROOM, "R2-208"),
                        new LocationNode(LocationTypes.REFRIGERATOR, "K1"),
                        new LocationNode(LocationTypes.SHELF, "3"))),
                Arguments.of("R203.6.1.1", List.of(
                        new LocationNode(LocationTypes.BUILDING, "R"),
                        new LocationNode(LocationTypes.ROOM, "R203"),
                        new LocationNode(LocationTypes.REFRIGERATOR, "K6"),
                        new LocationNode(LocationTypes.SHELF, "1")))
        );
    }
}
