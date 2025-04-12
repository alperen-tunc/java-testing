package gmbh.conteco.seminarverwaltung.repository;

import gmbh.conteco.seminarverwaltung.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// ✅ data.sql wird geladen, da spring.sql.init.mode nicht überschrieben wird
@DataJpaTest(
        properties = {
                "spring.sql.init.mode=never"
        }
)
public class SeminarRepositoryWithEntityManagerAndUserWithoutDataSqlIntegrationTest {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SeminarRepository seminarRepository;

    @Autowired
    private SeminartagRepository seminartagRepository;

    @Test
    void shouldPersistSeminarWithUser() {
        // Arrange: Dozent speichern
        User oliver = new User();
        oliver.setUsername("Oliver");
        oliver.setIsDozent(true);
        entityManager.persist(oliver);

        // Arrange: Seminar speichern
        Seminar seminar = Seminar.builder()
                .titel("Clean Code")
                .dozent(oliver)
                .seminarId("S0002")
                .status(SeminarStatus.GEPLANT)
                .art(SeminarArt.PRAESENZ)
                .teilnehmerPraesenz(5)
                .location("Köln")
                .link("https://www.conteco.gmbh/generative-ki-masterclass-ein-eigenes-llm-bauen-und-trainieren/")
                .build();

        entityManager.persist(seminar);
        entityManager.flush();

        // Arrange, Act, Assert
        // Given, When, Then

        // Act
        List<Seminar> result = seminarRepository.findByStatus(SeminarStatus.GEPLANT);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitel()).isEqualTo("Clean Code");
        assertThat(result.get(0).getDozent()).isEqualTo(oliver);
        assertThat(result.get(0).getDozent().getUsername()).isEqualTo("Oliver");
    }
}