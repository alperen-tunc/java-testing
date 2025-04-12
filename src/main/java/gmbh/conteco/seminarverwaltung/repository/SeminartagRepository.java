package gmbh.conteco.seminarverwaltung.repository;

import gmbh.conteco.seminarverwaltung.domain.Seminartag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SeminartagRepository extends JpaRepository<Seminartag, Long> {
    List<Seminartag> findBySeminarId(UUID seminarId);
    void deleteBySeminarId(UUID seminarId);
}
