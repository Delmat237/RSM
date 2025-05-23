// entity/Formation.java
package com.rsm.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "formations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Formation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @ManyToOne
    @JoinColumn(name = "responsable_id")
    private Enseignant responsable; // C’est un enseignant comme les autres
}
