// entity/Formation.java
package reservation.rsm.entity;

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

    @OneToOne
    private Utilisateur responsable; // enseignant responsable
}
