package gmbh.conteco.seminarverwaltung.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Seminar {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private UUID id;

    private String seminarId;

    @Column(nullable = false)
    private String titel;

    private int teilnehmerPraesenz;
    private int teilnehmerOnline;

    private String location;

    private String link;

    @Column(length = 5000)
    private String agenda;

    @Column(length = 5000)
    private String beschreibung;

    @Enumerated(EnumType.STRING)
    private SeminarStatus status;

    @Enumerated(EnumType.STRING)
    private SeminarArt art;

    @ManyToOne
    private User dozent;

    @ManyToOne
    private Kunde kunde;

    private LocalDateTime quelldatum;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
