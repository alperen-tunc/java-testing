package gmbh.conteco.seminarverwaltung.controller;

import gmbh.conteco.seminarverwaltung.dto.SeminarDto;
import gmbh.conteco.seminarverwaltung.dto.SeminarMitTagenDto;
import gmbh.conteco.seminarverwaltung.service.SeminarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/seminare")
@RequiredArgsConstructor
@Tag(name = "Seminare", description = "Verwaltung von Seminaren")
public class SeminarController {

    private final SeminarService seminarService;

    @Operation(summary = "Alle Seminare abrufen")
    @GetMapping
    public List<SeminarMitTagenDto> getAll() {
        return seminarService.getAll();
    }

    @Operation(summary = "Ein Seminar abrufen")
    @GetMapping("/{id}")
    public SeminarMitTagenDto getById(@PathVariable UUID id) {
        return seminarService.getById(id);
    }

    @Operation(summary = "Ein neues Seminar erstellen")
    @PostMapping
    public ResponseEntity<SeminarMitTagenDto> create(@Valid @RequestBody SeminarDto dto) {
        SeminarMitTagenDto created = seminarService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Ein bestehendes Seminar aktualisieren")
    @PutMapping("/{id}")
    public SeminarMitTagenDto update(@PathVariable UUID id, @Valid @RequestBody SeminarDto dto) {
        return seminarService.update(id, dto);
    }

    @Operation(summary = "Ein Seminar löschen")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        seminarService.delete(id);
        return ResponseEntity.noContent().build();
    }
}