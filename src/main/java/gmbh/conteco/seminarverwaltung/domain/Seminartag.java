package gmbh.conteco.seminarverwaltung.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Seminartag {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    private Seminar seminar;

    @Column(nullable = false)
    private LocalDate tagDatum;

    private LocalTime startUhrzeit = LocalTime.of(9, 0);
    private LocalTime endUhrzeit = LocalTime.of(17, 0);
}
