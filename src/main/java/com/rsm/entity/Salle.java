// entity/Salle.java
package com.rsm.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "salles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private int capacite;
    private boolean disponibilite;
}
