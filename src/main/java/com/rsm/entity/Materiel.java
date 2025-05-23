// entity/Materiel.java
package com.rsm.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "materiels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Materiel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    private String marque;

    @Enumerated(EnumType.STRING)
    private TypeMateriel type;

    private boolean disponible;
}

