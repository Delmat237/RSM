package com.rsm.controller;

import com.rsm.entity.Materiel;
import com.rsm.payload.ApiResponse;
import com.rsm.service.MaterielService;
import com.rsm.util.ResponseUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materiels")
@Tag(name = "Matériels", description = "Gestion des matériels pédagogiques")
public class MaterielController {

    private final MaterielService materielService;

    public MaterielController(MaterielService materielService) {
        this.materielService = materielService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllMateriels() {
        List<Materiel> list = materielService.getAllMateriels();
        return ResponseEntity.ok(ResponseUtil.success("Liste des matériels", list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Materiel>> getMaterielById(@PathVariable Long id) {
        return materielService.getMaterielById(id)
                .map(materiel -> ResponseEntity.ok(ResponseUtil.success("Matériel trouvé", materiel)))
                .orElse(ResponseEntity.status(404).body(ResponseUtil.error("Matériel non trouvé")));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<?>> createMateriel(@RequestBody Materiel materiel) {
        Materiel created = materielService.createMateriel(materiel);
        return ResponseEntity.ok(ResponseUtil.success("Matériel créé avec succès", created));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateMateriel(@PathVariable Long id, @RequestBody Materiel materiel) {
        try {
            Materiel updated = materielService.updateMateriel(id, materiel);
            return ResponseEntity.ok(ResponseUtil.success("Matériel mis à jour", updated));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(ResponseUtil.error("Matériel non trouvé"));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteMateriel(@PathVariable Long id) {
        try {
            materielService.deleteMateriel(id);
            return ResponseEntity.ok(ResponseUtil.success("Matériel supprimé", null));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(ResponseUtil.error("Matériel non trouvé"));
        }
    }
}
