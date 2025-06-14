// entity/Reservation.java
package com.rsm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public  abstract class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime  dateDebut;

    @Column(nullable = false)
    private LocalDateTime dateFin;

    private LocalTime heureDebut;
    private LocalTime heureFin;


    @ManyToOne
    @JsonIgnore
    private Enseignant enseignant;

    @Enumerated(EnumType.STRING)
    private StatutReservation statut;

    @OneToMany
    private Set<Materiel> materiels;


}
