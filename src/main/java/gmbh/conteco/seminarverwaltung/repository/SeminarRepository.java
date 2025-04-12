package gmbh.conteco.seminarverwaltung.repository;

import gmbh.conteco.seminarverwaltung.domain.Seminar;
import gmbh.conteco.seminarverwaltung.domain.SeminarStatus;
import gmbh.conteco.seminarverwaltung.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface SeminarRepository extends JpaRepository<Seminar, UUID> {
    List<Seminar> findByStatus(SeminarStatus status);

    @Query("""
        SELECT COUNT(s) > 0
        FROM Seminar s
        JOIN Seminartag t ON t.seminar = s
        WHERE s.seminarId = :seminarId
          AND s.dozent = :dozent
          AND t.tagDatum = :startDatum
    """)
    boolean existsBySeminarIdAndDozentAndStartDatum(
            @Param("seminarId") String seminarId,
            @Param("dozent") User dozent,
            @Param("startDatum") LocalDate startDatum
    );
}
