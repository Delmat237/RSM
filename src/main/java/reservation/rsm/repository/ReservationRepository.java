package reservation.rsm.repository;


import reservation.rsm.entity.Reservation;
import reservation.rsm.entity.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Vérifier les réservations conflictuelles pour une salle donnée
    List<Reservation> findBySalleAndDateFinAfterAndDateDebutBefore(Salle salle, LocalDateTime dateDebut, LocalDateTime dateFin);
}

