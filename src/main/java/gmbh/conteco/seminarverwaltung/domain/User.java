package gmbh.conteco.seminarverwaltung.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnDefault;

@Data
@Entity(name = "users")
@EqualsAndHashCode(exclude = {"username"})
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    @ColumnDefault("true")
    private Boolean isDozent = true;
}
