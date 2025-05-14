package reservation.rsm.service;

import reservation.rsm.entity.*;
import reservation.rsm.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final SalleRepository salleRepository;
    private final MaterielRepository materielRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              SalleRepository salleRepository,
                              MaterielRepository materielRepository) {
        this.reservationRepository = reservationRepository;
        this.salleRepository = salleRepository;
        this.materielRepository = materielRepository;
    }

    public boolean estDisponible(Salle salle, LocalDateTime debut, LocalDateTime fin) {
        List<Reservation> conflits = reservationRepository.findBySalleAndDateFinAfterAndDateDebutBefore(salle, debut, fin);
        return conflits.isEmpty();
    }

    public Reservation reserverSalleMateriel(Utilisateur enseignant, Long salleId, Set<Long> materielIds,
                                             LocalDateTime debut, LocalDateTime fin) throws Exception {
        Salle salle = salleRepository.findById(salleId)
                .orElseThrow(() -> new Exception("Salle non trouvée"));

        if (!estDisponible(salle, debut, fin)) {
            throw new Exception("Salle non disponible sur ce créneau");
        }

        List<Materiel> materielList = materielRepository.findAllById((Iterable<Long>) materielIds);
        Set<Materiel> materiels = materielList.stream()
                .filter(Materiel::isDisponible)
                .collect(Collectors.toSet());
        if (materiels.size() != materielIds.size()) {
            throw new Exception("Certains matériels ne sont pas disponibles");
        }

        Reservation reservation = new Reservation();
        reservation.setSalle(salle);
        reservation.setMateriels(materiels);
        reservation.setDateDebut(debut);
        reservation.setDateFin(fin);
        reservation.setEnseignant(enseignant);

        // Marquer matériel comme non disponible
        materiels.forEach(m -> m.setDisponible(false));
        materielRepository.saveAll(materiels);

        return reservationRepository.save(reservation);
    }

    // Autres méthodes: annulation, consultation planning, récapitulatif, etc.
}
