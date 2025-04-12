package gmbh.conteco.seminarverwaltung.repository;

import gmbh.conteco.seminarverwaltung.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(
        properties = {
                "spring.sql.init.mode=never"
        }
)
@Sql(scripts = "/sql/test-setup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/test-cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class SeminarRepositoryWithEntityManagerAndKundeWithSqlScriptsIntegrationTest {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SeminarRepository seminarRepository;

    @Autowired
    private SeminartagRepository seminartagRepository;

    @Test
    @DisplayName("shouldPersistSeminarWithKundeAndSeminartageUsingCustomSql")
    void shouldPersistSeminarWithKundeAndSeminartage() {
        List<Kunde> kunden = entityManager.createQuery("SELECT k FROM Kunde k WHERE k.name = :name", Kunde.class)
                .setParameter("name", "CONTECO e.U.")
                .getResultList();

        // 1. Sicherstellen, dass data.sql geladen ist.
        assertThat(kunden).hasSize(1);

        // 2. Seminar erzeugen
        Seminar seminar = new Seminar();
        seminar.setTitel("Clean Code");
        seminar.setKunde(kunden.get(0));
        seminar.setSeminarId("S0002");
        seminar.setStatus(SeminarStatus.GEPLANT);
        seminar.setArt(SeminarArt.PRAESENZ);
        seminar.setTeilnehmerOnline(0);
        seminar.setTeilnehmerPraesenz(5);
        seminar.setLocation("Köln");
        seminar.setLink("https://www.conteco.gmbh/generative-ki-masterclass-ein-eigenes-llm-bauen-und-trainieren/");

        seminarRepository.save(seminar);

        // 3. Seminartage erzeugen
        LocalDate start = LocalDate.of(2025, 4, 9);
        LocalDate end = LocalDate.of(2025, 4, 11);
        for (LocalDate tag = start; !tag.isAfter(end); tag = tag.plusDays(1)) {
            var seminarTag = new Seminartag();
            seminarTag.setSeminar(seminar);
            seminarTag.setTagDatum(tag);
            seminarTag.setStartUhrzeit(LocalTime.of(9,0));
            seminarTag.setEndUhrzeit(LocalTime.of(16,0));

            seminartagRepository.save(seminarTag);
        }

        // 4. Verifikation: Seminar gefunden und Kunde vorhanden
        List<Seminar> seminare = seminarRepository.findByStatus(SeminarStatus.GEPLANT);
        assertThat(seminare).hasSize(1);
        Seminar savedSeminar = seminare.get(0);
        assertThat(savedSeminar.getKunde()).isNotNull();
        assertThat(savedSeminar.getKunde().getName()).isEqualTo("CONTECO e.U.");

        // 5. Verifikation: Seminartag korrekt gespeichert
        List<Seminartag> tage = seminartagRepository.findBySeminarId(savedSeminar.getId());
        assertThat(tage).hasSize(3);
        assertThat(tage.get(0).getStartUhrzeit()).isEqualTo(LocalTime.of(9, 0));
    }
}