package gmbh.conteco.seminarverwaltung.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(exclude = {"name"})
public class Kunde {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;
}
