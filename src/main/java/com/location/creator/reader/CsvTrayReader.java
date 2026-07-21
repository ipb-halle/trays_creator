package com.location.creator.reader;

import com.location.creator.domain.LocationResolver;
import com.location.creator.domain.Tray;
import com.location.creator.domain.TraySize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Parst EINE Tray-CSV aus dem Classpath in eine Liste von {@link Tray}.
 * Weiß nichts von Datenbank – reine Lese-/Parse-Verantwortung.
 *
 * <p>CSV-Aufbau: Zeile 1 = "Traygröße: XX", Zeile 2 = Spaltenüberschriften,
 * danach Datenzeilen im Wechsel mit Leerzeilen (";;;;;;").
 */
@Slf4j
@Component
public class CsvTrayReader {

    private static final String DELIMITER = ";";
    private static final int META_LINES = 2;

    public List<Tray> read(TraySize size) throws IOException {
        List<Tray> trays = new ArrayList<>();

        InputStream in = getClass().getClassLoader().getResourceAsStream(size.csvPath());
        if (in == null) {
            throw new IOException("CSV nicht im Classpath gefunden: " + size.csvPath());
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                // Meta-Zeilen (Traygröße + Überschriften) überspringen.
                if (lineNumber <= META_LINES) {
                    continue;
                }

                // Leerzeilen wie ";;;;;;" überspringen.
                if (isBlankRow(line)) {
                    continue;
                }

                trays.add(parseLine(size, line, lineNumber));
            }
        }

        log.info("{}: {} Trays gelesen", size, trays.size());
        return trays;
    }

    private Tray parseLine(TraySize size, String line, int lineNumber) {
        String[] cols = line.split(DELIMITER, -1);
        String standort = col(cols, 1);
        return new Tray(
                size,
                col(cols, 0),
                standort,
                LocationResolver.fromStandort(standort),
                col(cols, 2),
                col(cols, 3),
                col(cols, 4),
                parseSpalten(col(cols, 5), size, lineNumber),
                col(cols, 6)
        );
    }

    private boolean isBlankRow(String line) {
        return line.isBlank() || line.replace(DELIMITER, "").isBlank();
    }

    /** Liefert die Spalte oder "" wenn sie fehlt. */
    private String col(String[] cols, int index) {
        return index < cols.length ? cols[index] : "";
    }

    /** "Spalten"-Feld ist eine Zahl; leere/ungültige Werte werden zu null. */
    private Integer parseSpalten(String value, TraySize size, int lineNumber) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            log.warn("{} Zeile {}: 'Spalten' nicht numerisch: '{}'", size, lineNumber, value);
            return null;
        }
    }
}
