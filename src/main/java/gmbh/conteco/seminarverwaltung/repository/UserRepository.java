package gmbh.conteco.seminarverwaltung.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gmbh.conteco.seminarverwaltung.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndIsDozentTrue(Long username);

    Optional<User> findByUsernameAndIsDozentTrue(String username);
}
