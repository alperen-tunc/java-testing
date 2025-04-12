package gmbh.conteco.seminarverwaltung.dto;

import gmbh.conteco.seminarverwaltung.domain.SeminarArt;
import gmbh.conteco.seminarverwaltung.domain.SeminarStatus;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import java.util.List;
import java.util.UUID;

public record SeminarMitTagenDto(
        UUID id,
        String seminarId,
        @NotBlank
        String titel,
        String dozentUsername,
        SeminarStatus status,
        SeminarArt art,
        int teilnehmerPraesenz,
        int teilnehmerOnline,
        String location,
        @URL
        String link,
        List<SeminartagDto> seminartage
) {}