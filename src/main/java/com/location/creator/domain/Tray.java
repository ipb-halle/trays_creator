package com.location.creator.domain;

/**
 * Fachliches Modell eines Trays – eine Zeile aus einer Tray-CSV.
 * Reines Domain-Objekt: keine Abhängigkeit zu DB oder CSV.
 */
public record Tray(
        TraySize size,
        String nummer,
        String standort,
        Rooms rootLocation,
        String datumAusgabe,
        String datumRueckgabe,
        String fuellstand,
        Integer spalten,
        String zeilen
) {
}
