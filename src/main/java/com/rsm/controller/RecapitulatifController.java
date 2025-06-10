package com.rsm.controller;

import com.rsm.entity.ReservationSalle;
import com.rsm.entity.StatutReservation;
import com.rsm.entity.Utilisateur;
import com.rsm.payload.ApiResponse;
import com.rsm.security.CustomUserDetailsService;
import com.rsm.service.ReservationService;
import com.rsm.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recapitulatif")
@RequiredArgsConstructor
@Tag(name = "Récapitulatif", description = "Gestion des récapitulatifs de réservations")
public class RecapitulatifController {

    private final CustomUserDetailsService utilisateurService;
    private final ReservationService reservationService;

    @Operation(summary = "Récapitulatif des réservations de salle d'un enseignant")
    @PreAuthorize("hasRole('ENSEIGNANT')")
    @GetMapping("/salle")
    public ResponseEntity<ApiResponse<?>> getTeacherRoomReservations(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {
        Utilisateur user = getCurrentUser(userDetails);
        return ResponseEntity.ok(ResponseUtil.success(
                "Récapitulatif des réservations de salle",
                reservationService.getTeacherReservations(user.getId())
        ));
    }

    @Operation(summary = "Récapitulatif horaire d'un enseignant avec total des heures")
    @PreAuthorize("hasRole('ENSEIGNANT')")
    @GetMapping("/horaire/enseignant")
    public ResponseEntity<ApiResponse<?>> getTeacherSchedule(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {
        Utilisateur user = getCurrentUser(userDetails);

        // 1. Récupérer toutes les réservations de l'enseignant
        List<ReservationSalle> reservations = reservationService.getTeacherRoomReservations(user.getId());

        // 2. Filtrer pour ne garder que les cours (statut ACCEPTEE)
        List<ReservationSalle> cours = reservations.stream()
                .filter(r -> r.getStatut() == StatutReservation.ACCEPTEE)
                .collect(Collectors.toList());

        // 3. Calculer la durée totale
        long totalMinutes = cours.stream()
                .mapToLong(r -> Duration.between(r.getDateDebut(), r.getDateFin()).toMinutes())
                .sum();

        // 4. Convertir en heures et minutes
        long hours = totalMinutes / 60;
        long minutes = totalMinutes % 60;

        // 5. Créer la réponse
        Map<String, Object> response = new HashMap<>();
        response.put("reservations", cours);
        response.put("totalHeures", String.format("%d heures et %d minutes", hours, minutes));
        response.put("totalMinutes", totalMinutes);

        return ResponseEntity.ok(ResponseUtil.success(
                "Récapitulatif horaire enseignant avec total",
                response
        ));
    }

    @Operation(summary = "Récapitulatif horaire par formation")
    @PreAuthorize("hasAnyRole('ENSEIGNANT', 'ADMIN')")
    @GetMapping("/horaire/formation")
    public ResponseEntity<ApiResponse<?>> getFormationSchedule(
            @Parameter(description = "ID de la formation", required = true)
            @RequestParam Long formationId,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {

        Utilisateur user = getCurrentUser(userDetails);
        return ResponseEntity.ok(ResponseUtil.success(
                "Récapitulatif horaire de la formation",
                "reservationService.getFormationSchedule(formationId, user.getId())"
        ));
    }

    @Operation(summary = "Récapitulatif des réservations de matériel d'un enseignant")
    @PreAuthorize("hasRole('ENSEIGNANT')")
    @GetMapping("/materiel")
    public ResponseEntity<ApiResponse<?>> getTeacherMaterialReservations(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {
        Utilisateur user = getCurrentUser(userDetails);
        return ResponseEntity.ok(ResponseUtil.success(
                "Récapitulatif des réservations de matériel",
                reservationService.getMaterialReservationById(user.getId())
        ));
    }

    private Utilisateur getCurrentUser(UserDetails userDetails) {
        return (Utilisateur) utilisateurService.loadUserByUsername(userDetails.getUsername());
    }
}