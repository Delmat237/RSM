package com.rsm.entity;

import lombok.*;
import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "etudiants")
public class Etudiant extends Utilisateur {
    // Pas de comportements spécifiques pour l’instant
}
