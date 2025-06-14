package com.rsm.repository;

import com.rsm.entity.ReservationSalle;
import com.rsm.entity.StatutReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

import java.util.List;

public interface ReservationSalleRepository extends JpaRepository<ReservationSalle, Long> {

    // Improved conflict detection query
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
            "FROM ReservationSalle r " +
            "WHERE r.salle.id = :salleId " +
            "AND ((r.dateDebut < :fin) AND (r.dateFin > :debut)) " +
            "AND r.statut <> 'REFUSEE'")
    boolean existsConflictingReservation(@Param("salleId") Long salleId,
                                         @Param("debut") LocalDateTime debut,
                                         @Param("fin") LocalDateTime fin);

    // Find all reservations for a specific teacher
    List<ReservationSalle> findByEnseignantId(Long enseignantId);

    // Find reservations by status
    List<ReservationSalle> findByStatut(StatutReservation statut);

    // Find current reservations (ongoing now)
    @Query("SELECT r FROM ReservationSalle r " +
            "WHERE r.dateDebut <= CURRENT_TIMESTAMP " +
            "AND r.dateFin >= CURRENT_TIMESTAMP " +
            "AND r.salle.id = :salleId")
    List<ReservationSalle> findCurrentReservationsForSalle(@Param("salleId") Long salleId);

    // Find upcoming reservations for a room
    @Query("SELECT r FROM ReservationSalle r " +
            "WHERE r.dateDebut > CURRENT_TIMESTAMP " +
            "AND r.salle.id = :salleId " +
            "ORDER BY r.dateDebut ASC")
    List<ReservationSalle> findUpcomingReservationsForSalle(@Param("salleId") Long salleId);

    // Update reservation status
    @Modifying
    @Query("UPDATE ReservationSalle r SET r.statut = :statut WHERE r.id = :id")
    void updateStatus(@Param("id") Long id, @Param("statut") StatutReservation statut);

    // Find reservations for a room within a date range
    @Query("SELECT r FROM ReservationSalle r " +
            "WHERE r.salle.id = :salleId " +
            "AND r.dateDebut BETWEEN :startDate AND :endDate " +
            "ORDER BY r.dateDebut ASC")
    List<ReservationSalle> findBySalleAndDateRange(
            @Param("salleId") Long salleId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    // Check room availability
    @Query("SELECT CASE WHEN COUNT(r) = 0 THEN true ELSE false END " +
            "FROM ReservationSalle r " +
            "WHERE r.salle.id = :salleId " +
            "AND ((r.dateDebut < :fin) AND (r.dateFin > :debut)) " +
            "AND r.statut <> 'REFUSEE'")
    boolean isSalleAvailable(@Param("salleId") Long salleId,
                             @Param("debut") LocalDateTime debut,
                             @Param("fin") LocalDateTime fin);

    // Find overlapping reservations
    @Query("SELECT r FROM ReservationSalle r " +
            "WHERE r.salle.id = :salleId " +
            "AND ((r.dateDebut BETWEEN :debut AND :fin) OR " +
            "(r.dateFin BETWEEN :debut AND :fin) OR " +
            "(r.dateDebut <= :debut AND r.dateFin >= :fin))")
    List<ReservationSalle> findOverlappingReservations(
            @Param("salleId") Long salleId,
            @Param("debut") LocalDateTime debut,
            @Param("fin") LocalDateTime fin);

    boolean existsBySalleIdAndDateDebutLessThanEqualAndDateFinGreaterThanEqual(Long salleId, LocalDateTime fin, LocalDateTime debut);

    @Query("SELECT r FROM ReservationSalle r ORDER BY r.dateDebut ASC")
    List<ReservationSalle> findAllByOrderByDateDebutAsc();

    @Query("SELECT r FROM ReservationSalle r WHERE r.salle.id = :salleId ORDER BY r.dateDebut ASC")
    List<ReservationSalle> findBySalleIdOrderByDateDebutAsc(@Param("salleId") Long salleId);


    @Query("SELECT r FROM ReservationSalle r " +
            "WHERE r.dateDebut >= :start AND r.dateFin <= :end " +
            "ORDER BY r.dateDebut ASC")
           List<ReservationSalle> findBetweenDates(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

@Query("SELECT COUNT(r) > 0 FROM ReservationSalle r " +
       "WHERE r.enseignant.id = :enseignantId " +
       "AND r.dateDebut <= :dateDebut AND r.dateFin >= :dateFin")
        boolean hasReservedSalle(@Param("enseignantId") Long enseignantId,
                         @Param("dateDebut") LocalDateTime dateDebut,
                         @Param("dateFin") LocalDateTime dateFin);

}