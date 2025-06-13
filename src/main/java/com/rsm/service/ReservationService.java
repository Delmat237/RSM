package com.rsm.service;

import com.rsm.entity.*;
import com.rsm.exception.ConflictException;
import com.rsm.exception.ResourceNotFoundException;
import com.rsm.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
public class ReservationService {

    private final ReservationSalleRepository salleReservationRepository;
    private final ReservationMaterielRepository materielReservationRepository;
    private final SalleRepository salleRepository;
    private final MaterielRepository materielRepository;

    @Autowired
    public ReservationService(ReservationSalleRepository salleReservationRepository,
                              ReservationMaterielRepository materielReservationRepository,
                              SalleRepository salleRepository,
                              MaterielRepository materielRepository) {
        this.salleReservationRepository = salleReservationRepository;
        this.materielReservationRepository = materielReservationRepository;
        this.salleRepository = salleRepository;
        this.materielRepository = materielRepository;
    }

    // Room Reservation Methods

    @Transactional
    public ReservationSalle reserverSalle(Enseignant enseignant, Long salleId,
                                          LocalDateTime dateDebut, LocalDateTime dateFin,
                                          String motif) throws Exception {
        validateReservationPeriod(dateDebut, dateFin);

        Salle salle = salleRepository.findById(salleId)
                .orElseThrow(() -> new ResourceNotFoundException("Salle non trouvée"));

        if (hasRoomConflict(salleId, dateDebut, dateFin)) {
            throw new ConflictException("La salle est déjà réservée pour cette période");
        }

        ReservationSalle reservation = new ReservationSalle();
        reservation.setEnseignant(enseignant);
        reservation.setSalle(salle);
        reservation.setDateDebut(dateDebut.toLocalDate());
        reservation.setHeureDebut(dateDebut.toLocalTime());
        reservation.setHeureFin(dateFin.toLocalTime());
        reservation.setDateFin(dateFin.toLocalDate());
        reservation.setMotif(motif);
        reservation.setStatut(StatutReservation.EN_ATTENTE);

        return salleReservationRepository.save(reservation);
    }

    // Material Reservation Methods

    @Transactional
    public ReservationMateriel reserverMateriel(Utilisateur enseignant, Long materielId,
                                                LocalDateTime dateDebut, LocalDateTime dateFin,
                                                String motif) throws Exception {
        validateReservationPeriod(dateDebut, dateFin);




        Materiel materiel = materielRepository.findById(materielId)
                .orElseThrow(() -> new ResourceNotFoundException("Materiel non trouvée"));

        if (hasRoomConflict(materielId, dateDebut, dateFin)) {
            throw new ConflictException("La salle est déjà réservée pour cette période");
        }

        ReservationMateriel reservation = new ReservationMateriel();
        reservation.setEnseignant((Enseignant) enseignant);
        reservation.setMateriel(materiel);
        reservation.setDateDebut(dateDebut.toLocalDate());
        reservation.setDateFin(dateFin.toLocalDate());
    
        reservation.setMotif(motif);
        reservation.setStatut(StatutReservation.EN_ATTENTE);

        return materielReservationRepository.save(reservation);
    }

    // Common CRUD Operations

    public List<ReservationSalle> getAllRoomReservations() {
        return salleReservationRepository.findAllByOrderByDateDebutAsc();
    }

    public List<ReservationMateriel> getAllMaterialReservations() {
        return materielReservationRepository.findAll();
    }

    public Optional<ReservationSalle> getRoomReservationById(Long id) {
        return salleReservationRepository.findById(id);
    }

    public Optional<ReservationMateriel> getMaterialReservationById(Long id) {
        return materielReservationRepository.findById(id);
    }

    @Transactional
    public void cancelReservation(Long id) throws ResourceNotFoundException {
        if (salleReservationRepository.existsById(id)) {
            salleReservationRepository.deleteById(id);
        } else if (materielReservationRepository.existsById(id)) {
            materielReservationRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Réservation non trouvée");
        }
    }

    // Status Management

    @Transactional
    public void updateReservationStatus(Long id, StatutReservation statut) throws ResourceNotFoundException {
        if (salleReservationRepository.existsById(id)) {
            salleReservationRepository.updateStatus(id, statut);
        } else if (materielReservationRepository.existsById(id)) {
            materielReservationRepository.updateStatus(id, statut);
        } else {
            throw new ResourceNotFoundException("Réservation non trouvée");
        }
    }

    // Teacher-specific Methods

    public List<Reservation> getTeacherReservations(Long enseignantId) {
        List<Reservation> reservations = new ArrayList<>();
        reservations.addAll(salleReservationRepository.findByEnseignantId(enseignantId));
        reservations.addAll(materielReservationRepository.findByEnseignantId(enseignantId));
        return reservations;
    }

    // Availability Checks

    private boolean hasRoomConflict(Long salleId, LocalDateTime debut, LocalDateTime fin) {
        return salleReservationRepository.existsBySalleIdAndDateDebutLessThanEqualAndDateFinGreaterThanEqual(
                salleId, fin, debut);
    }

    private boolean hasMaterialConflict(Long materielId, LocalDateTime debut, LocalDateTime fin) {
        return materielReservationRepository.existsByMaterielIdAndDateDebutLessThanEqualAndDateFinGreaterThanEqual(
                materielId, fin, debut);
    }

    // Validation Methods

    private void validateReservationPeriod(LocalDateTime debut, LocalDateTime fin) throws IllegalArgumentException {
        if (debut.isAfter(fin)) {
            throw new IllegalArgumentException("La date de début doit être antérieure à la date de fin");
        }
        if (debut.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La réservation ne peut pas être dans le passé");
        }
    }


    public Reservation update(Long id, Reservation reservation) throws ResourceNotFoundException {
        if (salleReservationRepository.existsById(id)) {
            salleReservationRepository.updateStatus(id, reservation.getStatut());
        } else if (materielReservationRepository.existsById(id)) {
            materielReservationRepository.updateStatus(id, reservation.getStatut());
        } else {
            throw new ResourceNotFoundException("Réservation non trouvée");
        }
        return reservation;
    }



    public List<ReservationSalle> getRoomReservations(Long salleId) throws ResourceNotFoundException {
        if (!salleRepository.existsById(salleId)) {
            throw new ResourceNotFoundException("Salle non trouvée avec l'ID: " + salleId);
        }
        return salleReservationRepository.findBySalleIdOrderByDateDebutAsc(salleId);
    }


    public boolean isSalleAvailable(Long salleId, LocalDateTime debut, LocalDateTime fin) throws ResourceNotFoundException {
        if (debut.isAfter(fin)) {
            throw new IllegalArgumentException("La date de début doit être antérieure à la date de fin");
        }

        if (!salleRepository.existsById(salleId)) {
            throw new ResourceNotFoundException("Salle non trouvée avec l'ID: " + salleId);
        }

        return !salleReservationRepository.existsConflictingReservation(
                salleId,
                debut,
                fin
        );
    }


    public List<ReservationSalle> getReservationsBetweenDates(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("La date de début doit être antérieure à la date de fin");
        }

        return salleReservationRepository.findBetweenDates(start, end);
    }

    public List<ReservationSalle> getTeacherRoomReservations(Long enseignantId) {
        return salleReservationRepository.findByEnseignantId(enseignantId);
    }
}
