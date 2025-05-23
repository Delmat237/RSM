package com.rsm.controller;

import com.rsm.entity.Salle;
import com.rsm.payload.ApiResponse;
import com.rsm.service.SalleService;
import com.rsm.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salles")
public class SalleController {

    private final SalleService salleService;

    public SalleController(SalleService salleService) {
        this.salleService = salleService;
    }

    // Accessible à tous : liste des salles
    @GetMapping
    public ResponseEntity<ApiResponse<List<Salle>>> getAllSalles() {
        List<Salle> salles = salleService.getAllSalles();
        return ResponseEntity.ok(ResponseUtil.success("Liste des salles", salles));
    }

    // Accessible à tous : salle par ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Salle>> getSalleById(@PathVariable Long id) {
        return salleService.getSalleById(id)
                .map(salle -> ResponseEntity.ok(ResponseUtil.success("Salle trouvée", salle)))
                .orElse(ResponseEntity.status(404).body(ResponseUtil.error("Salle non trouvée")));
    }

    // Création (ADMIN uniquement)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<Salle>> createSalle(@RequestBody Salle salle) {
        Salle nouvelleSalle = salleService.createSalle(salle);
        return ResponseEntity.ok(ResponseUtil.success("Salle créée avec succès", nouvelleSalle));
    }

    // Mise à jour (ADMIN uniquement)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateSalle(@PathVariable Long id, @RequestBody Salle salle) {
        try {
            Salle updated = salleService.updateSalle(id, salle);
            return ResponseEntity.ok(ResponseUtil.success("Salle mise à jour", updated));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(ResponseUtil.error("Salle non trouvée"));
        }
    }

    // Suppression (ADMIN uniquement)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteSalle(@PathVariable Long id) {
        try {
            salleService.deleteSalle(id);
            return ResponseEntity.ok(ResponseUtil.success("Salle supprimée", null));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(ResponseUtil.error("Salle non trouvée"));
        }
    }
}
