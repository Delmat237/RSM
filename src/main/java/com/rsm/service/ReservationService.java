package com.rsm.service;

import com.rsm.entity.Materiel;
import com.rsm.entity.Reservation;
import com.rsm.entity.Salle;
import com.rsm.entity.StatutReservation;
import com.rsm.entity.Utilisateur;
import com.rsm.repository.MaterielRepository;
import com.rsm.repository.ReservationRepository;
import com.rsm.repository.SalleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SalleRepository salleRepository;

    @Autowired
    private MaterielRepository materielRepository;

    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getByEnseignantId(Long enseignantId) {
        return reservationRepository.findByEnseignantId(enseignantId);
    }

    public boolean isCreneauDisponible(LocalDateTime date, Date debut, Date fin, Long salleId) {
        return reservationRepository.countConflicts(date, debut, fin, salleId) == 0;
    }

    public boolean hasConflit(LocalDateTime debut, LocalDateTime fin, Long salleId) {
        return reservationRepository.existsBySalleIdAndDateDebutLessThanEqualAndDateFinGreaterThanEqual(
                salleId, fin, debut
        );
    }


    @Transactional
    public Reservation reserver(Reservation reservation) throws Exception {
        boolean dispo = isCreneauDisponible(reservation.getDateDebut(), reservation.getHeureDebut(), reservation.getHeureFin(), reservation.getSalle().getId());
        if (!dispo) {
            throw new Exception("Créneau non disponible pour la salle sélectionnée");
        }
        if (hasConflit(reservation.getDateDebut(), reservation.getDateFin(), reservation.getSalle().getId())) {
            throw new Exception("Il y a un conflit de réservation avec la salle.");
        }


        Salle salle = salleRepository.findById(reservation.getSalle().getId())
                .orElseThrow(() -> new Exception("Salle non trouvée"));

        if (reservation.getMateriels() != null) {
            Set<Materiel> materiels = reservation.getMateriels();
            if (materiels != null && !materiels.isEmpty()) {
                for (Materiel materiel : materiels) {
                    materielRepository.findById(materiel.getId())
                            .orElseThrow(() -> new Exception("Matériel non trouvé"));
                    // Validation of material existence is sufficient here
                }
                reservation.setMateriels(materiels);
            }
        }

        reservation.setSalle(salle);
        reservation.setStatut(StatutReservation.EN_ATTENTE);
        return reservationRepository.save(reservation);
    }


     public Reservation reserverSalleMateriel(Utilisateur enseignant, Long salleId, Set<Long> materielIds, LocalDateTime dateDebut, LocalDateTime dateFin) {
        // Implement the logic for reserving a room and materials
        // For now, return a dummy Reservation object
        return new Reservation();
    }

    public void supprimer(Long id) {
        reservationRepository.deleteById(id);
    }

    public Optional<Reservation> getById(Long id) {
        return reservationRepository.findById(id);
    }

    @Transactional
    public Reservation update(Long id, Reservation reservationDetails) throws Exception {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new Exception("Réservation non trouvée"));

        reservation.setDateDebut(reservationDetails.getDateDebut());
        reservation.setDateFin(reservationDetails.getDateFin());
        reservation.setHeureDebut(reservationDetails.getHeureDebut());
        reservation.setHeureFin(reservationDetails.getHeureFin());

        Salle salle = salleRepository.findById(reservationDetails.getSalle().getId())
                .orElseThrow(() -> new Exception("Salle non trouvée"));
        reservation.setSalle(salle);

        Set<Materiel> materiels = reservationDetails.getMateriels();
        if (materiels != null && !materiels.isEmpty()) {
            for (Materiel materiel : materiels) {
                materielRepository.findById(materiel.getId())
                        .orElseThrow(() -> new Exception("Matériel non trouvé"));
            }
            reservation.setMateriels(materiels);
        }

        return reservationRepository.save(reservation);
    }

    public void mettreAJourStatut(Long reservationId, StatutReservation statut) throws Exception {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new Exception("Réservation non trouvée"));
        reservation.setStatut(statut);
        reservationRepository.save(reservation);
    }

    public List<Reservation> getRécapitulatifParEnseignant(Long enseignantId) {
        return reservationRepository.findByEnseignantId(enseignantId);
    }


}
