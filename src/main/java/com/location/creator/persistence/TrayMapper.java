package com.location.creator.persistence;

import com.location.creator.domain.Tray;

/**
 * Übersetzt zwischen Domain-{@link Tray} und JPA-{@link TrayEntity}.
 * Hält das Fachmodell frei von Hibernate.
 */
public final class TrayMapper {

    private TrayMapper() {
        throw new AssertionError("Keine Instanzen!");
    }

    public static TrayEntity toEntity(Tray tray) {
        return TrayEntity.builder()
                .traySize(tray.size())
                .nummer(tray.nummer())
                .standort(tray.standort())
                .rootLocation(tray.rootLocation())
                .datumAusgabe(tray.datumAusgabe())
                .datumRueckgabe(tray.datumRueckgabe())
                .fuellstand(tray.fuellstand())
                .spalten(tray.spalten())
                .zeilen(tray.zeilen())
                .build();
    }

    public static Tray toDomain(TrayEntity entity) {
        return new Tray(
                entity.getTraySize(),
                entity.getNummer(),
                entity.getStandort(),
                entity.getRootLocation(),
                entity.getDatumAusgabe(),
                entity.getDatumRueckgabe(),
                entity.getFuellstand(),
                entity.getSpalten(),
                entity.getZeilen()
        );
    }
}
