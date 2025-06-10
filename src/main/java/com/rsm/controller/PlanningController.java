package com.rsm.controller;

import com.rsm.entity.ReservationSalle;
import com.rsm.exception.ResourceNotFoundException;
import com.rsm.payload.ApiResponse;
import com.rsm.service.ReservationService;
import com.rsm.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/planning")
@RequiredArgsConstructor
@Tag(name = "Planning", description = "Gestion des plannings des salles")
public class PlanningController {

    private final ReservationService reservationService;

    @Operation(summary = "Récupérer le planning complet")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ReservationSalle>>> getPlanning() {
        List<ReservationSalle> planning = reservationService.getAllRoomReservations();
        return ResponseEntity.ok(ResponseUtil.success("Planning récupéré avec succès", planning));
    }

    @Operation(summary = "Vérifier la disponibilité d'une salle")
    @GetMapping("/disponibilite")
    public ResponseEntity<ApiResponse<Boolean>> verifierDisponibilite(
            @Parameter(description = "ID de la salle", required = true)
            @RequestParam Long salleId,

            @Parameter(description = "Date et heure de début (format: yyyy-MM-ddTHH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime debut,

            @Parameter(description = "Date et heure de fin (format: yyyy-MM-ddTHH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) throws ResourceNotFoundException {

        boolean disponible = reservationService.isSalleAvailable(salleId, debut, fin);
        return ResponseEntity.ok(ResponseUtil.success("Disponibilité vérifiée", disponible));
    }

    @Operation(summary = "Récupérer le planning d'une salle spécifique")
    @GetMapping("/salles/{salleId}")
    public ResponseEntity<ApiResponse<List<ReservationSalle>>> getPlanningSalle(
            @PathVariable Long salleId) throws ResourceNotFoundException {
        List<ReservationSalle> planning = reservationService.getRoomReservations(salleId);
        return ResponseEntity.ok(ResponseUtil.success("Planning de la salle récupéré", planning));
    }

    @Operation(summary = "Récupérer le planning pour une période")
    @GetMapping("/periode")
    public ResponseEntity<ApiResponse<List<ReservationSalle>>> getPlanningPeriode(
            @Parameter(description = "Date de début (format: yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,

            @Parameter(description = "Date de fin (format: yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {

        List<ReservationSalle> planning = reservationService.getReservationsBetweenDates(
                dateDebut.atStartOfDay(),
                dateFin.plusDays(1).atStartOfDay()
        );
        return ResponseEntity.ok(ResponseUtil.success("Planning pour la période demandée", planning));
    }
}