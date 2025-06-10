package com.rsm.repository;

import com.rsm.entity.ReservationMateriel;
import com.rsm.entity.StatutReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface ReservationMaterielRepository extends JpaRepository<ReservationMateriel, Long> {

    // Conflict detection query (improved version)
    @Query("SELECT COUNT(r) > 0 FROM ReservationMateriel r " +
            "JOIN r.materiel m " +
            "WHERE m.id =: materielId " +
            "AND ((r.dateDebut < :fin) AND (r.dateFin > :debut)) " +
            "AND r.statut <> 'REFUSEE'")
    boolean existsConflictingReservation(@Param("materielId") Long materielId,
                                         @Param("debut") LocalDateTime debut,
                                         @Param("fin") LocalDateTime fin);

    // Find all reservations for a teacher
    List<ReservationMateriel> findByEnseignantId(Long enseignantId);

    // Find reservations by status
    List<ReservationMateriel> findByStatut(StatutReservation statut);

    // Find current reservations (ongoing now)
    @Query("SELECT r FROM ReservationMateriel r " +
            "WHERE r.dateDebut <= CURRENT_TIMESTAMP " +
            "AND r.dateFin >= CURRENT_TIMESTAMP")
    List<ReservationMateriel> findCurrentReservations();

    // Find upcoming reservations
    @Query("SELECT r FROM ReservationMateriel r " +
            "WHERE r.dateDebut > CURRENT_TIMESTAMP " +
            "ORDER BY r.dateDebut ASC")
    List<ReservationMateriel> findUpcomingReservations();

    // Update reservation status
    @Modifying
    @Query("UPDATE ReservationMateriel r SET r.statut = :statut WHERE r.id = :id")
    void updateStatus(@Param("id") Long id, @Param("statut") StatutReservation statut);



    boolean existsByMaterielIdAndDateDebutLessThanEqualAndDateFinGreaterThanEqual(
            @Param("materielId") Long materielId,
            @Param("fin") LocalDateTime fin,
            @Param("debut") LocalDateTime debut);
}