package gmbh.conteco.seminarverwaltung.mapper;

import gmbh.conteco.seminarverwaltung.domain.Seminar;
import gmbh.conteco.seminarverwaltung.domain.SeminarArt;
import gmbh.conteco.seminarverwaltung.domain.Seminartag;
import gmbh.conteco.seminarverwaltung.domain.User;
import gmbh.conteco.seminarverwaltung.dto.SeminarDto;
import gmbh.conteco.seminarverwaltung.dto.SeminarMitTagenDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SeminarMapperTest {
    private final SeminarMapper mapper = new SeminarMapperImpl();

    @Test
    void shouldMapDtoToEntity() {
        SeminarDto dto = new SeminarDto(
                UUID.randomUUID(), "S0001",
                "TDD", "Oliver",
                null, SeminarArt.HYBRID, 3, 2,
                "Wuppertal", "https://www.google.de",
                LocalDateTime.now(), LocalDateTime.now().plusDays(1)
        );

        Seminar seminar = mapper.toEntity(dto);

        assertThat(seminar.getTitel()).isEqualTo("TDD");
        assertThat(seminar.getLocation()).isEqualTo("Wuppertal");
    }

    @Test
    void testDozentUsernameMappingInDtoMitTagen() {
        Seminar seminar = new Seminar();
        User dozent = new User();
        dozent.setUsername("Oliver");
        seminar.setDozent(dozent);

        List<Seminartag> tage = List.of();

        SeminarMitTagenDto dto = mapper.toDtoMitTagen(seminar, tage);

        Assertions.assertEquals("Oliver", dto.dozentUsername());
    }


    @Test
    void testSeminartageMappingInDtoMitTagen() {
        Seminar seminar = new Seminar();
        User dozent = new User();
        dozent.setUsername("Oliver");
        seminar.setDozent(dozent);

        Seminartag tag1 = new Seminartag();
        tag1.setTagDatum(LocalDate.of(2025, 5, 12));
        tag1.setStartUhrzeit(LocalTime.of(9, 0));
        tag1.setEndUhrzeit(LocalTime.of(16, 0));

        Seminartag tag2 = new Seminartag();
        tag2.setTagDatum(LocalDate.of(2025, 5, 13));
        tag2.setStartUhrzeit(LocalTime.of(9, 0));
        tag2.setEndUhrzeit(LocalTime.of(16, 0));

        List<Seminartag> tage = List.of(tag1, tag2);

        SeminarMitTagenDto dto = mapper.toDtoMitTagen(seminar, tage);

        Assertions.assertEquals("Oliver", dto.dozentUsername());
        Assertions.assertEquals(2, dto.seminartage().size());

        Assertions.assertEquals(LocalDate.of(2025, 5, 12), dto.seminartage().get(0).tagDatum());
        Assertions.assertEquals(LocalTime.of(9, 0), dto.seminartage().get(0).startUhrzeit());
        Assertions.assertEquals(LocalTime.of(16, 0), dto.seminartage().get(0).endUhrzeit());

        Assertions.assertEquals(LocalDate.of(2025, 5, 13), dto.seminartage().get(1).tagDatum());
    }
}
