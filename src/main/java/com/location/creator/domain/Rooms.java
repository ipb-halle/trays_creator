package com.location.creator.domain;

/**
 * Kuratierte Root-Rooms (Räume) laut IPB/SNB-Nummerierungsschema.
 * Der {@code code} ist die reale Schreibweise im {@code standort}-String
 * (z.B. mit Bindestrich bei {@code R2-109}), da der Java-Enum-Name keinen
 * Bindestrich enthalten darf.
 */
public enum Rooms {
    R002("R002"),
    R003("R003"),
    R007("R007"),
    R104("R104"),
    R203("R203"),
    R2_109("R2-109"),
    R2_207("R2-207"),
    R2_208("R2-208"),
    R2_304("R2-304"),
    D106("D106");

    private final String code;

    Rooms(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }
}
