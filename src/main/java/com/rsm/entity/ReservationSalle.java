package com.rsm.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservations_salle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReservationSalle extends Reservation {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salle_id", nullable = false)
    private Salle salle;

    @Column(length = 500)
    private String motif; // Conférence, formation, réunion, etc.

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutReservation statut = StatutReservation.EN_ATTENTE;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enseignant_id", nullable = false)
    private Enseignant enseignant;

    // Business methods
    public boolean isActive() {
        LocalDateTime now = LocalDateTime.now();
        return getDateDebut().isBefore(now) && getDateFin().isAfter(now);
    }

    public boolean isUpcoming() {
        return getDateDebut().isAfter(LocalDateTime.now());
    }

    public boolean isPast() {
        return getDateFin().isBefore(LocalDateTime.now());
    }
}