// entity/Utilisateur.java
package com.rsm.entity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    @Column(unique = true)
    private String email;
    private String matricule;
    private String motDePasse;

    @Enumerated(EnumType.STRING)
    private Role role;  // Enum: ENSEIGNANT, ETUDIANT, RESPONSABLE
}

