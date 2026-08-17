package com.location.creator.domainTest;

import com.location.creator.domain.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TrayStandortParserTest {

    @Test
    public void parseTray_putsValidStandortIntoResolved() {
        List<String> strings = List.of("R2-208K1-3");
        TrayParseResult result = buildTrayParseResult(strings);
        Tray testTray = buildTray("R2-208K1-3");

        assertThat(result.resolved().get(0).tray()).isEqualTo(testTray);
        List<LocationNode> nodes = List.of(
                new LocationNode(LocationTypes.ROOM, "R2-208", "R2-208"),
                new LocationNode(LocationTypes.REFRIGERATOR, "K1","R2-208.K1" ),
                new LocationNode(LocationTypes.SHELF, "3", "3")
        );
        assertThat(result.resolved().get(0).path()).containsExactlyElementsOf(nodes);
    }

    @Test
    public void parseTray_putsUnknownRoomIntoUnresolved() {
        List<String> strings = List.of("Trockenlager");
        TrayParseResult result = buildTrayParseResult(strings);
        Tray testTray = buildTray("Trockenlager");

        assertThat(result.unresolved().get(0).tray()).isEqualTo(testTray);
        assertThat(result.unresolved().get(0).reason()).isEqualTo(UnresolvedReason.NOT_VALID_ROOM);
    }

    @Test
    public void parseTray_putsBlankStandortIntoUnresolved() {
        List<String> strings = List.of("     ");
        TrayParseResult result = buildTrayParseResult(strings);
        Tray testTray = buildTray("     ");

        assertThat(result.unresolved().get(0).tray()).isEqualTo(testTray);
        assertThat(result.unresolved().get(0).reason()).isEqualTo(UnresolvedReason.EMPTY_PATH);
    }

    @Test
    public void parseTray_returnsEmptyResultForEmptyInput(){
        List<Tray> empty = List.of();
        TrayParseResult result = TrayStandortParser.parseTray(empty);
        TrayParseResult test = new TrayParseResult(List.of(), List.of());
        assertThat(result).isEqualTo(test);
    }

    private TrayParseResult buildTrayParseResult(List<String> standorte) {
        List<Tray> trays = buildTrayList(standorte);
        return TrayStandortParser.parseTray(trays);
    }

    private List<Tray> buildTrayList(List<String> standorte) {
        return standorte.stream().map(this::buildTray).toList();
    }

    private Tray buildTray(String standort) {
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
