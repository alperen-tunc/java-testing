package gmbh.conteco.seminarverwaltung.repository;

import gmbh.conteco.seminarverwaltung.domain.Seminar;
import gmbh.conteco.seminarverwaltung.domain.SeminarStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
public class SeminarRepositoryIntegrationTest {
    @Autowired
    SeminarRepository seminarRepository;

    @Test
    void shouldPersistAndFindByStatus() {
        Seminar seminar = new Seminar();
        seminar.setTitel("TDD Advanced");
        seminar.setStatus(SeminarStatus.GEPLANT);

        Seminar result = seminarRepository.save(seminar);

        List<Seminar> results = seminarRepository.findByStatus(SeminarStatus.GEPLANT);

        assertThat(results).hasSize(1);
        assertThat(results.get(0)).isEqualTo(result);
    }
}
