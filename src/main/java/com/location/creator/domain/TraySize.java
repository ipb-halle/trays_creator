package com.location.creator.domain;

/**
 * Traygröße als Kategorie. Kennt die zugehörige CSV-Datei im Classpath.
 * Die konkreten Maße (Spalten/Zeilen) hängen NICHT hier fest, weil sie
 * pro Tray variieren – sie stehen als Daten am {@link Tray}.
 */
public enum TraySize {
    TS("csv/ts_trays.csv"),
    TM("csv/tm_trays.csv"),
    TL("csv/tl_trays.csv"),
    TH("csv/th_trays.csv");

    private final String csvPath;

    TraySize(String csvPath) {
        this.csvPath = csvPath;
    }

    public String csvPath() {
        return csvPath;
    }
}
