// entity/Reservation.java
package reservation.rsm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

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

    @ManyToOne
    private Salle salle;

    @ManyToMany
    @JoinTable(name = "reservation_materiels",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "materiel_id"))
    private Set<Materiel> materiels;

    @ManyToOne
    private Utilisateur enseignant;
}
