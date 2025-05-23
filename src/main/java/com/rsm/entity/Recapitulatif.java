package com.rsm.entity;

import lombok.*;
import jakarta.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recapitulatif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Enseignant enseignant;

    @ManyToOne
    private Formation formation;

    private int totalHeures;
}
