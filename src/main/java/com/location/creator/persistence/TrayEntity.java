package com.location.creator.persistence;

import com.location.creator.domain.Locations;
import com.location.creator.domain.TraySize;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "trays",
        indexes = @Index(name = "idx_trays_tray_size", columnList = "tray_size")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TrayEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tray_size", nullable = false)
    private TraySize traySize;

    @Column
    private String nummer;

    @Column
    private String standort;

    @Enumerated(EnumType.STRING)
    @Column(name = "root_location")
    private Locations rootLocation;

    @Column(name = "datum_ausgabe")
    private String datumAusgabe;

    @Column(name = "datum_rueckgabe")
    private String datumRueckgabe;

    @Column
    private String fuellstand;

    @Column
    private Integer spalten;

    @Column
    private String zeilen;
}
