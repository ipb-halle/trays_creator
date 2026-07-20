package com.location.creator.api;

import com.location.creator.domain.Tray;
import com.location.creator.service.TrayImportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TrayController {

    private final TrayImportService importService;

    public TrayController(TrayImportService importService) {
        this.importService = importService;
    }

    /** Liest alle CSVs, speichert sie in die DB und gibt die Trays zurück. */
    @GetMapping("/trays/import")
    public List<Tray> importTrays() throws IOException {
        return importService.importAll();
    }
}
