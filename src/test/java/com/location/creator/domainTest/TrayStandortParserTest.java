package com.location.creator.domainTest;

import com.location.creator.domain.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TrayStandortParserTest {


    @Test
    public void parseTray_splitsTraysIntoResolvedAndUnresolved() {
        List<Tray> trays = buildTestList();
        TrayParseResult trayParseResult = TrayStandortParser.parseTray(trays);

        assertThat(trayParseResult.resolved().size() + trayParseResult.unresolved().size()).isEqualTo(trays.size());
        assertThat(trayParseResult.resolved().size()).isEqualTo(1);
        assertThat(trayParseResult.unresolved().size()).isEqualTo(2);
        List<LocationNode> nodes = List.of(
                new LocationNode(LocationTypes.ROOM, "R2-208"),
                new LocationNode(LocationTypes.REFRIGERATOR, "K1"),
                new LocationNode(LocationTypes.SHELF, "3")
        );
        assertThat(trayParseResult.resolved().get(0).path()).containsExactlyElementsOf(nodes);
        assertThat(trayParseResult.resolved().get(0).tray()).isEqualTo(trays.get(0));

        assertThat(trayParseResult.unresolved().get(0).reason()).isEqualTo(UnresolvedReason.NOT_VALID_ROOM);
        assertThat(trayParseResult.unresolved().get(0).tray()).isEqualTo(trays.get(1));

        assertThat(trayParseResult.unresolved().get(1).reason()).isEqualTo(UnresolvedReason.EMPTY_PATH);
        assertThat(trayParseResult.unresolved().get(1).tray()).isEqualTo(trays.get(2));

        List<Tray> empty = List.of();
        TrayParseResult result = TrayStandortParser.parseTray(empty);
        TrayParseResult test = new TrayParseResult(List.of(), List.of());
        assertThat(result).isEqualTo(test);

    }


    private List<Tray> buildTestList() {
        List<String> standorte = List.of("R2-208K1-3", "Trockenlager", "   ");
        return standorte.stream().map(this::testDataBuilder).toList();
    }

    private Tray testDataBuilder(String standort) {
        return new Tray(
                TraySize.TS,
                "testNummer",
                standort,
                LocationResolver.fromStandort(standort),
                "testdatum1",
                "testdatum2",
                "voll",
                10,
                "D");
    }

}
