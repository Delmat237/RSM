package com.rsm.repository;

import com.rsm.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.dateDebut = :date AND r.salle.id = :salleId AND " +
           "((:debut BETWEEN r.heureDebut AND r.heureFin) OR (:fin BETWEEN r.heureDebut AND r.heureFin) OR " +
           "(:debut <= r.heureDebut AND :fin >= r.heureFin))")
    long countConflicts(@Param("date") LocalDateTime date,
                        @Param("debut") Date debut,
                        @Param("fin") Date fin,
                        @Param("salleId") Long salleId);

    List<Reservation> findByEnseignantId(Long enseignantId);
    boolean existsBySalleIdAndDateDebutLessThanEqualAndDateFinGreaterThanEqual(Long salleId, LocalDateTime fin, LocalDateTime debut);


}
