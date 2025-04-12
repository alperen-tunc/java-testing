package gmbh.conteco.seminarverwaltung.dto;

import gmbh.conteco.seminarverwaltung.domain.SeminarArt;
import gmbh.conteco.seminarverwaltung.domain.SeminarStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;
import java.util.UUID;

public record SeminarDto (
        @Schema(description = "Seminar-ID")
        UUID id,
        @Schema(description = "Seminar-Kennung bzw. Seminar-Nummer", example = "S1001")
        String seminarId,
        @NotBlank
        @Schema(description = "Titel des Seminars", requiredMode = Schema.RequiredMode.REQUIRED, example = "Next-Level AI: Generative KI & LLMs in der Praxis")
        String titel,
        @Schema(description = "Benutzername des Dozenten", example = "Oliver")
        String dozentUsername,
        @Schema(description = "Status des Seminars wie abgesagt oder verrechnet etc.", example = "GEPLANT")
        SeminarStatus status,
        @Schema(description = "Wird das Seminars vor Ort, remote oder hybrid durchgeführt?", example = "ONLINE")
        SeminarArt art,
        @Schema(description = "Anzahl der Präsenzteilnehmer", example = "0")
        int teilnehmerPraesenz,
        @Schema(description = "Anzahl der Online-Teilnehmer", example = "5")
        int teilnehmerOnline,
        @Schema(description = "Seminarstandort", example = "Online")
        String location,
        @URL
        @Schema(description = "Link zum Onlineseminar oder Link zur Seminarbeschreibung auf der Webseite", example = "https://www.conteco.gmbh/next-level-ai-generative-ki-llms-in-der-praxis/")
        String link,
        @Schema(description = "Startzeitpunkt des Seminars", example = "2025-06-02T09:00:00.000Z")
        LocalDateTime startzeitpunkt,
        @Schema(description = "Endzeitpunkt des Seminars", example = "2025-06-06T17:00:00.000Z")
        LocalDateTime endzeitpunkt
) {}