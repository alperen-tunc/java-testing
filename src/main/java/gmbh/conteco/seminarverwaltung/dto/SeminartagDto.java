package gmbh.conteco.seminarverwaltung.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record SeminartagDto(
        LocalDate tagDatum,
        LocalTime startUhrzeit,
        LocalTime endUhrzeit
) {}