// entity/Utilisateur.java
package reservation.rsm.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "utilisateurs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    @Column(unique = true)
    private String email;
    private String motDePasse;

    @Enumerated(EnumType.STRING)
    private Role role;  // Enum: ENSEIGNANT, ETUDIANT, RESPONSABLE
}

