package gmbh.conteco.seminarverwaltung.service;

import gmbh.conteco.seminarverwaltung.domain.*;
import gmbh.conteco.seminarverwaltung.dto.SeminarDto;
import gmbh.conteco.seminarverwaltung.dto.SeminarMitTagenDto;
import gmbh.conteco.seminarverwaltung.dto.SeminartagDto;
import gmbh.conteco.seminarverwaltung.mapper.SeminarMapper;
import gmbh.conteco.seminarverwaltung.repository.SeminarRepository;
import gmbh.conteco.seminarverwaltung.repository.SeminartagRepository;
import gmbh.conteco.seminarverwaltung.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SeminarServiceImplTest {
    @Mock
    SeminarRepository seminarRepository;

    @Mock
    SeminartagRepository seminartagRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    SeminarMapper seminarMapper;

    @InjectMocks
    SeminarServiceImpl seminarService;

    @Test
    void shouldCreateSeminarAndGenerateSeminartage() {
        UUID seminarId = UUID.randomUUID();

        SeminarDto seminarDto = new SeminarDto(null, "S0003", "TDD 101", "Oliver", SeminarStatus.IN_DURCHFUEHRUNG, SeminarArt.HYBRID, 3, 4, "Wuppertal", "link", LocalDateTime.of(2025, 4, 9, 9, 9, 0), LocalDateTime.of(2025, 4, 11, 16, 0, 0));

        User oliver = new User();
        oliver.setUsername("Oliver");
        oliver.setId(1L);

        Seminar seminar = new Seminar();
        seminar.setId(seminarId);
        seminar.setTitel("TDD 101");
        seminar.setDozent(oliver);
        seminar.setStatus(SeminarStatus.IN_DURCHFUEHRUNG);
        seminar.setArt(SeminarArt.HYBRID);
        seminar.setTeilnehmerPraesenz(3);
        seminar.setTeilnehmerOnline(4);
        seminar.setLocation("Wuppertal");
        seminar.setLink("link");

        List<Seminartag> seminartage = List.of(createTag(seminar, 2025, 4, 9), createTag(seminar, 2025, 4, 10), createTag(seminar, 2025, 4, 11), createTag(seminar, 2025, 4, 12));

        SeminarMitTagenDto expectedDto = new SeminarMitTagenDto(
                seminarId,
                "S1007",
                "Generative KI im Büroalltag: Tools und Anwendungen für Anwender",
                "Oliver",
                SeminarStatus.IN_DURCHFUEHRUNG,
                SeminarArt.HYBRID,
                3,
                5,
                "Wuppertal",
                "https://www.conteco.gmbh/generative-ki-im-bueroalltag-tools-und-anwendungen-fuer-anwender/",
                seminartage.stream().map(tag -> new SeminartagDto(tag.getTagDatum(), tag.getStartUhrzeit(), tag.getEndUhrzeit())).toList()
        );

        // Stubs
        when(userRepository.findByUsernameAndIsDozentTrue("Oliver")).thenReturn(Optional.of(oliver));
        when(seminarMapper.toEntity(seminarDto)).thenReturn(seminar);
        when(seminarRepository.save(any(Seminar.class))).thenReturn(seminar);
        when(seminartagRepository.saveAll(anyList())).thenReturn(seminartage);
        when(seminarMapper.toDtoMitTagen(eq(seminar), anyList())).thenReturn(expectedDto);

        // Act
        SeminarMitTagenDto result = seminarService.create(seminarDto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.titel()).isEqualTo("Generative KI im Büroalltag: Tools und Anwendungen für Anwender");
        assertThat(result.seminartage()).hasSize(4);
        assertThat(result.seminartage().get(0).tagDatum()).isEqualTo(LocalDate.of(2025, 4, 9));
        assertThat(result.seminartage().get(3).endUhrzeit()).isEqualTo(LocalTime.of(16, 0));

        // Verify
        verify(seminarRepository, times(1)).save(any());
        verify(seminartagRepository, times(1)).saveAll(any());
        verify(seminarMapper).toDtoMitTagen(eq(seminar), any());
    }

    private Seminartag createTag(Seminar seminar, int year, int month, int day) {
        Seminartag seminartag = new Seminartag();
        seminartag.setSeminar(seminar);
        seminartag.setTagDatum(LocalDate.of(year, month, day));
        seminartag.setStartUhrzeit(LocalTime.of(9, 0));
        seminartag.setEndUhrzeit(LocalTime.of(16, 0));
        return seminartag;
    }
}