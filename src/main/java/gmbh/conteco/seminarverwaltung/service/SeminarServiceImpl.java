package gmbh.conteco.seminarverwaltung.service;

import gmbh.conteco.seminarverwaltung.domain.Seminar;
import gmbh.conteco.seminarverwaltung.domain.Seminartag;
import gmbh.conteco.seminarverwaltung.domain.User;
import gmbh.conteco.seminarverwaltung.dto.SeminarDto;
import gmbh.conteco.seminarverwaltung.dto.SeminarMitTagenDto;
import gmbh.conteco.seminarverwaltung.mapper.SeminarMapper;
import gmbh.conteco.seminarverwaltung.repository.SeminarRepository;
import gmbh.conteco.seminarverwaltung.repository.SeminartagRepository;
import gmbh.conteco.seminarverwaltung.repository.UserRepository;
import gmbh.conteco.seminarverwaltung.service.exception.ApiException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SeminarServiceImpl implements SeminarService {
    private final SeminarRepository seminarRepository;
    private final SeminartagRepository seminartagRepository;
    private final SeminarMapper seminarMapper;
    private final UserRepository userRepository;

    private List<Seminartag> createSeminartage(Seminar seminar, LocalDateTime start, LocalDateTime end) {
        List <Seminartag> seminartage = new ArrayList<>();

        LocalDate startDate = start.toLocalDate();
        LocalDate endDate = end.toLocalDate();

        // Default-zeitfenster
        LocalTime defaultStart = LocalTime.of(9,0);
        LocalTime defaultEnd = LocalTime.of(16,0);

        // Logik: Uhrzeiten aus Start-/Endzeitpunkt nur übernehmen, wenn sie nicht auf 00:00 steht
        boolean useCustomStart = !start.toLocalTime().equals(LocalTime.MIDNIGHT);
        boolean useCustomEnd = !(end.toLocalTime().equals(LocalTime.MIDNIGHT) || (end.toLocalTime().equals(LocalTime.MAX)));

        // Falls explizite Uhrzeiten vergeben wurden, für alle Tage verwenden
        LocalTime appliedStart = useCustomStart ? start.toLocalTime() : defaultStart;
        LocalTime appliedEnd = useCustomEnd ? end.toLocalTime() : defaultEnd;

        for (LocalDate tag = startDate; tag.isBefore(endDate.plusDays(1)); tag = tag.plusDays(1)) {
            Seminartag seminartag = new Seminartag();
            seminartag.setSeminar(seminar);
            seminartag.setTagDatum(tag);
            seminartag.setStartUhrzeit(appliedStart);
            seminartag.setEndUhrzeit(appliedEnd);

            seminartage.add(seminartag);
        }

        return seminartage;
    }

    @Override
    @Transactional
    public SeminarMitTagenDto create(SeminarDto dto) {
        User dozent = userRepository.findByUsernameAndIsDozentTrue(dto.dozentUsername()).orElseThrow(
                () -> new ApiException("Dozent nicht gefunden oder User ist kein Dozent")
        );

        if (seminarRepository.existsBySeminarIdAndDozentAndStartDatum(dto.seminarId(), dozent, dto.startzeitpunkt().toLocalDate())) {
            throw new ApiException("Ein Seminar mit derselben Seminar-ID, demselben Dozenten und Startzeitpunkt existiert bereits.");
        }

        Seminar seminar = seminarMapper.toEntity(dto);
        seminar.setId(null); // explizit als neue Entität kennzeichnen
        seminar.setDozent(dozent);

        Seminar savedSeminar = seminarRepository.save(seminar);

        List<Seminartag> tage = createSeminartage(savedSeminar, dto.startzeitpunkt(), dto.endzeitpunkt());
        seminartagRepository.saveAll(tage);

        return seminarMapper.toDtoMitTagen(savedSeminar, tage);
    }

    @Override
    @Transactional
    public SeminarMitTagenDto update(UUID id, SeminarDto dto) {
        Seminar existing = seminarRepository.findById(id)
                .orElseThrow(() -> new ApiException("Seminar nicht gefunden"));

        Seminar backup = new Seminar();
        BeanUtils.copyProperties(existing, backup);

        // Dozent aktualisieren, falls nötig
        if (dto.dozentUsername() != null && !dto.dozentUsername().isBlank()) {
            User dozent = userRepository.findByUsernameAndIsDozentTrue(dto.dozentUsername())
                    .orElseThrow(() -> new ApiException("Dozent nicht gefunden oder kein Dozent"));
            existing.setDozent(dozent);
        }

        // Eigenschaften setzen
        existing.setTitel(dto.titel());
        existing.setLocation(dto.location());
        existing.setStatus(dto.status());
        existing.setArt(dto.art());
        existing.setTeilnehmerPraesenz(dto.teilnehmerPraesenz());
        existing.setTeilnehmerOnline(dto.teilnehmerOnline());
        existing.setLink(dto.link());
        existing.setSeminarId(dto.seminarId());
        existing.setQuelldatum(dto.startzeitpunkt());

        Seminar updated = seminarRepository.save(existing);

        // Seminartage vorher löschen
        seminartagRepository.deleteBySeminarId(updated.getId());

        // Neue Tage generieren
        List<Seminartag> neueTage = createSeminartage(updated, dto.startzeitpunkt(), dto.endzeitpunkt());
        seminartagRepository.saveAll(neueTage);

        return seminarMapper.toDtoMitTagen(updated, neueTage);
    }

    // Achtung 2 Fallstricke:
    // 1. ich gehe hier pro Seminar an die Datenbank - jedes Mal einzeln und hole mir dann die Seminartage - wow!
    // 2. brauche ich wirklich alles Datensätze e.g. bei 2 Millionen Rows in einer Table?
    // 3. Caching
    // ...
    @Override
    public List<SeminarMitTagenDto> getAll() {
        return seminarRepository.findAll().stream().map(seminar ->
        {
            List<Seminartag> tage = seminartagRepository.findBySeminarId(seminar.getId());
            return seminarMapper.toDtoMitTagen(seminar, tage);
        }).toList();
    }

    @Override
    public SeminarMitTagenDto getById(UUID id) {
        Seminar seminar = seminarRepository.findById(id).orElseThrow(() -> new ApiException("Seminar nicht gefunden"));

        List<Seminartag> tage = seminartagRepository.findBySeminarId(seminar.getId());

        return seminarMapper.toDtoMitTagen(seminar, tage);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        // Ohne ON DELETE CASCADE:
        seminartagRepository.deleteBySeminarId(id);
        seminarRepository.deleteById(id);


        // mit ON DELETE CASCADE: -> Achtung 'cascade =' sezten bei @ManyToOne oder @OneToMany (abhängig vom Datenmodell)
        // seminarRepository.deleteById(id);
    }
}
