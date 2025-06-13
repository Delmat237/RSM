package com.rsm.controller;

import com.rsm.entity.Salle;
import com.rsm.payload.ApiResponse;
import com.rsm.service.SalleService;
import com.rsm.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salles")
@Tag(name = "Salles", description = "Gestion des salles ")
public class SalleController {

    private final SalleService salleService;

    public SalleController(SalleService salleService) {
        this.salleService = salleService;
    }

    @Operation(summary = "Récupérer la liste des salles")
    // Accessible à tous : liste des salles
    @GetMapping
    public ResponseEntity<ApiResponse<List<Salle>>> getAllSalles() {

        /*
        Endpoins permettant de recupérer toutes les salles enreistrées
         */
        List<Salle> salles = salleService.getAllSalles();
        return ResponseEntity.ok(ResponseUtil.success("Liste des salles", salles));
    }

    @Operation(summary = "Récupérer les caractéristiques d'une salle spécifique")
    // Accessible à tous : salle par ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Salle>> getSalleById(@PathVariable Long id) {
        return salleService.getSalleById(id)
                .map(salle -> ResponseEntity.ok(ResponseUtil.success("Salle trouvée", salle)))
                .orElse(ResponseEntity.status(404).body(ResponseUtil.error("Salle non trouvée")));
    }

    @Operation(summary = "Permet  d'enregistrer une nouvelles salles")
    // Création (ADMIN uniquement)
    @PreAuthorize("hasRole('ENSEIGNANT,RESPONSABLE')")
    @PostMapping
    public ResponseEntity<ApiResponse<Salle>> createSalle(@RequestBody Salle salle) {
        salleService.createSalle(salle);
        return ResponseEntity.ok(ResponseUtil.success("Salle créée avec succès", salle));
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
