package com.rsm.controller;

import com.rsm.entity.Reservation;
import com.rsm.entity.Utilisateur;
import com.rsm.entity.StatutReservation;
import com.rsm.payload.ApiResponse;
import com.rsm.security.CustomUserDetailsService;
import com.rsm.service.ReservationService;
import com.rsm.service.UtilisateurService;
import com.rsm.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    @Autowired
    private CustomUserDetailsService utilisateurService;


    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PreAuthorize("hasRole('ENSEIGNANT')")
    @PostMapping("/reserver")
    public ResponseEntity<ApiResponse<?>> reserverSalle(@AuthenticationPrincipal Utilisateur enseignant,
                                                        @RequestParam Long salleId,
                                                        @RequestParam Set<Long> materielIds,
                                                        @RequestParam String debut,
                                                        @RequestParam String fin) {
        try {
            LocalDateTime dateDebut = LocalDateTime.parse(debut);
            LocalDateTime dateFin = LocalDateTime.parse(fin);

            Reservation reservation = reservationService.reserverSalleMateriel(enseignant, salleId, materielIds, dateDebut, dateFin);
            return ResponseEntity.ok(ResponseUtil.success("Réservation effectuée avec succès", reservation));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseUtil.error(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ENSEIGNANT')")
    @PostMapping
    public ResponseEntity<ApiResponse<?>> reserver(@RequestBody Reservation reservation) {
        try {
            Reservation saved = reservationService.reserver(reservation);
            return ResponseEntity.ok(ResponseUtil.success("Réservation enregistrée", saved));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseUtil.error(e.getMessage()));
        }
    }

    @PreAuthorize("hasAnyRole('ENSEIGNANT','ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<Reservation>>> getAll() {
        return ResponseEntity.ok(ResponseUtil.success("Liste des réservations", reservationService.getAll()));
    }

    @PreAuthorize("hasRole('ENSEIGNANT')")
    @GetMapping("/enseignant/{id}")
    public ResponseEntity<ApiResponse<List<Reservation>>> getByEnseignant(@PathVariable Long id) {
        return ResponseEntity.ok(ResponseUtil.success("Réservations de l'enseignant", reservationService.getByEnseignantId(id)));
    }

    @PreAuthorize("hasRole('ENSEIGNANT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Long id) {
        reservationService.supprimer(id);
        return ResponseEntity.ok(ResponseUtil.success("Réservation supprimée"));
    }

    @PreAuthorize("hasRole('ENSEIGNANT')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> update(@PathVariable Long id, @RequestBody Reservation reservation) {
        try {
            Reservation updated = reservationService.update(id, reservation);
            return ResponseEntity.ok(ResponseUtil.success("Réservation mise à jour", updated));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseUtil.error(e.getMessage()));
        }
    }

    @PreAuthorize("hasAnyRole('ENSEIGNANT','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ResponseUtil.success("Réservation trouvée", reservationService.getById(id)));
    }

    @PreAuthorize("hasRole('RESPONSABLE')")
    @PatchMapping("/{id}/valider")
    public ResponseEntity<ApiResponse<?>> validerReservation(@PathVariable Long id) {
        try {
            reservationService.mettreAJourStatut(id, StatutReservation.ACCEPTEE);
            return ResponseEntity.ok(ResponseUtil.success("Réservation validée"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseUtil.error(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('RESPONSABLE')")
    @PatchMapping("/{id}/refuser")
    public ResponseEntity<ApiResponse<?>> refuserReservation(@PathVariable Long id) {
        try {
            reservationService.mettreAJourStatut(id, StatutReservation.REFUSEE);
            return ResponseEntity.ok(ResponseUtil.success("Réservation refusée"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseUtil.error(e.getMessage()));
        }
    }

    // ✅ Récapitulatif d'un enseignant connecté
   @PreAuthorize("hasRole('ENSEIGNANT')")
    @GetMapping("/enseignant")
   public ResponseEntity<?> recapitulatifEnseignant(@AuthenticationPrincipal UserDetails userDetails) {
        Utilisateur user = (Utilisateur) utilisateurService.loadUserByUsername(userDetails.getUsername());
        return ResponseEntity.ok( ResponseUtil.success(reservationService.getRécapitulatifParEnseignant(user.getId())).getData());
    }

}
