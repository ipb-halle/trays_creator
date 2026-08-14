package com.location.creator.domain;

/**
 * Traygröße als Kategorie. Kennt die zugehörige CSV-Datei im Classpath.
 * Die konkreten Maße (Spalten/Zeilen) hängen NICHT hier fest, weil sie
 * pro Tray variieren – sie stehen als Daten am {@link Tray}.
 */
public enum TraySize {
    TS("csv/ts_trays.csv", 3, 8),
    TM("csv/tm_trays.csv", 3, 8),
    TL("csv/tl_trays.csv", 3, 8),
    TH("csv/th_trays.csv", 3, 8);
    private final String csvPath;
    private final int column;
    private final int row;

    TraySize(String csvPath, int column, int row) {
        this.csvPath = csvPath;
        this.column = column;
        this.row = row;
    }

    public String csvPath() {
        return csvPath;
    }
}
