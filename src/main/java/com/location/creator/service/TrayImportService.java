package com.location.creator.service;

import com.location.creator.domain.Tray;
import com.location.creator.domain.TraySize;
import com.location.creator.persistence.TrayEntity;
import com.location.creator.persistence.TrayMapper;
import com.location.creator.persistence.TrayRepository;
import com.location.creator.reader.CsvTrayReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Orchestriert den Import: alle Tray-CSVs lesen, in die DB speichern und
 * als {@link List} von {@link Tray} zurückgeben.
 */
@Slf4j
@Service
public class TrayImportService {

    private final CsvTrayReader reader;
    private final TrayRepository repository;

    public TrayImportService(CsvTrayReader reader, TrayRepository repository) {
        this.reader = reader;
        this.repository = repository;
    }

    /**
     * Liest alle vier Tray-Größen, ersetzt den DB-Inhalt und liefert die
     * gespeicherten Trays. {@code deleteAll} macht den Import wiederholbar
     * (kein Duplikat-Aufbau bei mehrfachem Aufruf).
     */
    @Transactional
    public List<Tray> importAll() throws IOException {
        List<Tray> trays = new ArrayList<>();
        for (TraySize size : TraySize.values()) {
            trays.addAll(reader.read(size));
        }

        List<TrayEntity> entities = trays.stream()
                .map(TrayMapper::toEntity)
                .toList();

        repository.deleteAllInBatch();
        repository.saveAll(entities);

        log.info("Import abgeschlossen: {} Trays gespeichert", entities.size());
        return trays;
    }
}
