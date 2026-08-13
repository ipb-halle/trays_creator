package com.location.creator.domainTest;

import com.location.creator.domain.*;
import com.location.creator.persistence.TrayRepository;
import com.location.creator.reader.CsvTrayReader;
import com.location.creator.service.TrayImportService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TrayStandortParserTest {


    @Test
    public void test_trayStandortParser_tray_parse_result() {
        List<Tray> trays = new ArrayList<>();
        String r_path = "R2-208K1-3";
        String u_path = "Trockenlager";
        Tray rtray = testDataBuilder(r_path);
        Tray utray = testDataBuilder(u_path);
        trays.add(rtray);
        trays.add(utray);

        TrayParseResult trayParseResult = TrayStandortParser.parseTray(trays);
        assertThat(2).isEqualTo(trayParseResult.resolved().size() + trayParseResult.unresolved().size());
        assertThat(1).isEqualTo(trayParseResult.resolved().size());
        assertThat(1).isEqualTo(trayParseResult.unresolved().size());

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
