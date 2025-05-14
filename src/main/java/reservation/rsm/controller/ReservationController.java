package reservation.rsm.controller;

import reservation.rsm.entity.Reservation;
import reservation.rsm.entity.Utilisateur;
import reservation.rsm.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reserver")
    public ResponseEntity<?> reserverSalle(@AuthenticationPrincipal Utilisateur enseignant,
                                           @RequestParam Long salleId,
                                           @RequestParam Set<Long> materielIds,
                                           @RequestParam String debut,
                                           @RequestParam String fin) {
        try {
            LocalDateTime dateDebut = LocalDateTime.parse(debut);
            LocalDateTime dateFin = LocalDateTime.parse(fin);

            Reservation reservation = reservationService.reserverSalleMateriel(enseignant, salleId, materielIds, dateDebut, dateFin);
            return ResponseEntity.ok(reservation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Autres endpoints: consulter planning, récapitulatif, etc.
}
