// entity/Materiel.java
package reservation.rsm.entity;

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

    @Enumerated(EnumType.STRING)
    private TypeMateriel type;

    private boolean disponible;
}

