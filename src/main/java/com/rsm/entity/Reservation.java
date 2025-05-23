// entity/Reservation.java
package com.rsm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private Date heureDebut;
    private Date heureFin;
    private String motif;

    @ManyToOne
    private Salle salle;

    @ManyToMany
    @JoinTable(name = "reservation_materiels",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "materiel_id"))
    private Set<Materiel> materiels;

    @ManyToOne
    @JsonIgnore
    private Enseignant enseignant;

    @Enumerated(EnumType.STRING)
    private StatutReservation statut;

}
