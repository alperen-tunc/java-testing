package gmbh.conteco.seminarverwaltung.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import gmbh.conteco.seminarverwaltung.domain.User;
import gmbh.conteco.seminarverwaltung.dto.SeminarDto;
import gmbh.conteco.seminarverwaltung.dto.SeminarMitTagenDto;

public interface SeminarService {
    SeminarMitTagenDto create(SeminarDto dto);
    SeminarMitTagenDto update(UUID id, SeminarDto dto);
    List<SeminarMitTagenDto> getAll();
    SeminarMitTagenDto getById(UUID id);
    void delete(UUID id);
    Optional<User> getUserById(Long id);
}