package com.location.creator.domain;

/**
 * Leitet aus einem rohen {@code standort}-String die Root-Location (Raum) ab.
 *
 * <p>Normalisiert den Wert (trim, Großschreibung, Punkte/Leerzeichen entfernt)
 * und matcht per Präfix auf den {@link Locations#code()}. Werte, die keinen
 * bekannten Raum tragen (z.B. Person-/Fremd-IDs wie {@code 2503702} oder
 * benannte Lager wie {@code Trockenlager}), liefern {@code null}.
 */
public final class LocationResolver {

    private LocationResolver() {
    }

    public static Locations fromStandort(String standort) {
        if (standort == null) {
            return null;
        }
        String norm = standort.trim().toUpperCase()
                .replace(".", "")
                .replace(" ", "");
        if (norm.isEmpty()) {
            return null;
        }
        for (Locations location : Locations.values()) {
            if (norm.startsWith(location.code())) {
                return location;
            }
        }
        return null;
    }
}
