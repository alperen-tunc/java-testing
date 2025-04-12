package gmbh.conteco.seminarverwaltung.service;

import gmbh.conteco.seminarverwaltung.dto.SeminarDto;
import gmbh.conteco.seminarverwaltung.dto.SeminarMitTagenDto;

import java.util.List;
import java.util.UUID;

public interface SeminarService {
    SeminarMitTagenDto create(SeminarDto dto);
    SeminarMitTagenDto update(UUID id, SeminarDto dto);
    List<SeminarMitTagenDto> getAll();
    SeminarMitTagenDto getById(UUID id);
    void delete(UUID id);
}