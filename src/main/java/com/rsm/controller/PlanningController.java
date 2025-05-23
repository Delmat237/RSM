package com.rsm.controller;

import com.rsm.entity.Reservation;
import com.rsm.payload.ApiResponse;
import com.rsm.service.ReservationService;
import com.rsm.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/planning")
public class PlanningController {

    @Autowired
    private ReservationService reservationService;

    // Accessible à tous : enseignants + étudiants
    @GetMapping
    public ResponseEntity<ApiResponse<List<Reservation>>> getPlanning() {
        List<Reservation> planning = reservationService.getAll();
        return ResponseEntity.ok(ResponseUtil.success("Planning récupéré avec succès", planning));
    }

    // Vérifie la disponibilité d'une salle sur un créneau
    @GetMapping("/disponibilite")
    public ResponseEntity<ApiResponse<Boolean>> verifierDisponibilite(@RequestParam LocalDateTime date,
                                                                      @RequestParam Date heureDebut,
                                                                      @RequestParam Date heureFin,
                                                                      @RequestParam Long salleId) {
        boolean disponible = reservationService.isCreneauDisponible(date, heureDebut, heureFin, salleId);
        return ResponseEntity.ok(ResponseUtil.success("Disponibilité vérifiée", disponible));
    }
}
