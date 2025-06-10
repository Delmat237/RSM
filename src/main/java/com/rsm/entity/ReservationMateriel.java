package com.rsm.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "reservations_materiel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReservationMateriel extends Reservation {

    // Many-to-Many relationship with proper join table configuration
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "reservation_materiel_items",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "materiel_id")
    )
    private Materiel materiel ;

    @Column(length = 500)
    private String motif; // Reason for reservation

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutReservation statut = StatutReservation.EN_ATTENTE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enseignant_id", nullable = false)
    private Enseignant enseignant;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Business methods


    public boolean isActive() {
        LocalDateTime now = LocalDateTime.now();
        return getDateDebut().isBefore(now) && getDateFin().isAfter(now);
    }

    public boolean isUpcoming() {
        return getDateDebut().isAfter(LocalDateTime.now());
    }


}