package com.rsm.controller;

import com.rsm.entity.*;
import com.rsm.exception.ResourceNotFoundException;
import com.rsm.payload.ApiResponse;
import com.rsm.security.CustomUserDetailsService;
import com.rsm.service.ReservationService;
import com.rsm.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/reservations")
@Tag(name = "Reservatioon", description = "Gestion des   réservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final CustomUserDetailsService utilisateurService;


    @Autowired
    public ReservationController(ReservationService reservationService,
                                 CustomUserDetailsService utilisateurService) {
        this.reservationService = reservationService;
        this.utilisateurService = utilisateurService;
    }

    // Salle Reservation Endpoints

    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT','RESPONSABLE')")
    @PostMapping("/salles")
    public ResponseEntity<ApiResponse<?>> reserverSalle(
            @AuthenticationPrincipal Enseignant enseignant,
            @RequestParam Long salleId,
            @Parameter(description = "Date de debut (format: yyyy-MM-ddThh:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime debut,
            @Parameter(description = "Date de fin (format: yyyy-MM-ddThh:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin,
            @RequestParam String motif) {

        if (debut.isAfter(fin)) {
            return ResponseEntity.internalServerError()
                    .body(ResponseUtil.error("La date de début doit être antérieure à la date de fin"));
        }
        if (debut.isBefore(LocalDateTime.now())) {
            return ResponseEntity.internalServerError()
                    .body(ResponseUtil.error("La date de début ne peut pas être dans le passé"));
        }

        try {
            ReservationSalle reservation = reservationService.reserverSalle(enseignant, salleId, debut, fin, motif);
            return ResponseEntity.ok(ResponseUtil.success("Réservation de salle effectuée avec succès", reservation));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtil.error(e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.internalServerError()
                    .body(ResponseUtil.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ResponseUtil.error("Une erreur inattendue s'est produite : "+e.getMessage()));
        }
    }

    // Material Reservation Endpoints

    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT','RESPONSABLE')")
    @PostMapping("/materiels")
    public ResponseEntity<ApiResponse<?>> reserverMateriels(
            @AuthenticationPrincipal Enseignant enseignant,
            @RequestParam Long materielId,
            @Parameter(description = "Date de debut (format: yyyy-MM-ddThh:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime debut,
            @Parameter(description = "Date de fin (format: yyyy-MM-ddThh:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin,
            @RequestParam(required = false) String motif) {

        if (debut.isAfter(fin)) {
            return ResponseEntity.internalServerError()
                    .body(ResponseUtil.error("La date de début doit être antérieure à la date de fin"));
        }
        if (debut.isBefore(LocalDateTime.now())) {
            return ResponseEntity.internalServerError()
                    .body(ResponseUtil.error("La date de début ne peut pas être dans le passé"));
        }

        try {
            ReservationMateriel reservation = reservationService.reserverMateriel(enseignant, materielId, debut, fin, motif);
            return ResponseEntity.ok(ResponseUtil.success("Réservation de matériel effectuée avec succès", reservation));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtil.error(e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.internalServerError()
                    .body(ResponseUtil.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ResponseUtil.error("Une erreur inattendue s'est produite"));
        }
    }

    // Common CRUD Endpoints

    @PreAuthorize("hasAnyRole('RESPONSABLE','ENSEIGNANT','ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<java.util.Map<String, Object>>> getAllReservations() {
        java.util.Map<String, Object> reservationsMap = new java.util.HashMap<>();
        reservationsMap.put("salle", reservationService.getAllRoomReservations());
        reservationsMap.put("materiels", reservationService.getAllMaterialReservations());
        return ResponseEntity.ok(ResponseUtil.success("Liste des réservations", reservationsMap));
    }

    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT','RESPONSABLE')")
    @GetMapping("/enseignant/{id}")
    public ResponseEntity<ApiResponse<List<Reservation>>> getReservationsByEnseignant(@PathVariable Long id) {
        return ResponseEntity.ok(ResponseUtil.success("Réservations de l'enseignant",
                reservationService.getTeacherReservations(id)));
    }

    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT','RESPONSABLE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> annulerReservation(@PathVariable Long id) throws ResourceNotFoundException {
        reservationService.cancelReservation(id);
        return ResponseEntity.ok(ResponseUtil.success("Réservation annulée avec succès"));
    }

    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT','RESPONSABLE')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> modifierReservation(
            @PathVariable Long id,
            @RequestBody Reservation reservation) {
        try {
            Reservation updated = reservationService.update(id, reservation);
            return ResponseEntity.ok(ResponseUtil.success("Réservation mise à jour", updated));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseUtil.error(e.getMessage()));
        }
    }

    // Reservation Status Management

    @PreAuthorize("hasAnyRole('ADMIN','RESPONSABLE')")
    @PatchMapping("/{id}/statut")
    public ResponseEntity<ApiResponse<?>> modifierStatutReservation(
            @PathVariable Long id,
            @RequestParam StatutReservation statut) {
        try {
            reservationService.updateReservationStatus(id, statut);
            return ResponseEntity.ok(ResponseUtil.success("Statut de la réservation mis à jour"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseUtil.error(e.getMessage()));
        }
    }



}