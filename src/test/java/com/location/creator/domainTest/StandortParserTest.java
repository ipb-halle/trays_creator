package com.location.creator.domainTest;

import com.location.creator.domain.*;
import org.assertj.core.api.AssertionsForClassTypes;
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


        ParseResult parseResult = StandortParser.parsePath(path);
        List<LocationNode> locationNodes = parseResult.resolvedNodes();

        assertThat(locationNodes).containsExactlyElementsOf(expectedNodes);
        assertThat(parseResult.reason()).isNull();
        assertThat(parseResult.isResolved()).isTrue();
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
                Arguments.of(" D.106.K.1", List.of(
                        new LocationNode(LocationTypes.BUILDING, "D"),
                        new LocationNode(LocationTypes.ROOM, "D106"),
                        new LocationNode(LocationTypes.REFRIGERATOR, "K1"))),
                Arguments.of("R104G.1.1", List.of(
                        new LocationNode(LocationTypes.BUILDING, "R"),
                        new LocationNode(LocationTypes.ROOM, "R104"),
                        new LocationNode(LocationTypes.FREEZER, "G1"),
                        new LocationNode(LocationTypes.DRAWER, "1"))),
                Arguments.of("R2-109K1", List.of(
                        new LocationNode(LocationTypes.BUILDING, "R"),
                        new LocationNode(LocationTypes.ROOM, "R2-109"),
                        new LocationNode(LocationTypes.REFRIGERATOR, "K1"))),
                Arguments.of("R2-109K1.6", List.of(
                        new LocationNode(LocationTypes.BUILDING, "R"),
                        new LocationNode(LocationTypes.ROOM, "R2-109"),
                        new LocationNode(LocationTypes.REFRIGERATOR, "K1"),
                        new LocationNode(LocationTypes.SHELF, "6"))),
                Arguments.of("R2-109G.1", List.of(
                        new LocationNode(LocationTypes.BUILDING, "R"),
                        new LocationNode(LocationTypes.ROOM, "R2-109"),
                        new LocationNode(LocationTypes.FREEZER, "G1")))
        );
    }

    @ParameterizedTest
    @MethodSource("unresolvedClasses")
    public void parsePath_reportsReasonForUnresolvableStandort(String path, UnresolvedReason reason) {
        ParseResult result = StandortParser.parsePath(path);

        assertThat(result.reason()).isEqualTo(reason);
        assertThat(result.resolvedNodes()).isEmpty();
        AssertionsForClassTypes.assertThat(result.isResolved()).isFalse();
    }

    public static Stream<Arguments> unresolvedClasses() {
        return Stream.of(
                Arguments.of(null, UnresolvedReason.EMPTY_PATH),
                Arguments.of("", UnresolvedReason.EMPTY_PATH),
                Arguments.of("        ", UnresolvedReason.EMPTY_PATH),
                Arguments.of("MedikSchHa D", UnresolvedReason.NOT_VALID_ROOM),
                Arguments.of("05621", UnresolvedReason.NOT_VALID_ROOM),
                Arguments.of("R203.6.1.1", UnresolvedReason.NO_DEVICE),
                Arguments.of("R007 Trockla", UnresolvedReason.NO_DEVICE),
                Arguments.of("R2-208K1-3-2", UnresolvedReason.AMBIGUOUS_NUMBERS),
                Arguments.of("Trockenlager", UnresolvedReason.NOT_VALID_ROOM)
        );
    }
}
