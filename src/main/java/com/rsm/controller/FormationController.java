package com.rsm.controller;

import com.rsm.entity.Formation;
import com.rsm.service.FormationService;
import com.rsm.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/formations")
public class FormationController {

    @Autowired
    private FormationService formationService;

    // ✅ Obtenir toutes les formations
    @GetMapping
    public ResponseEntity<List<Formation>> getAllFormations() {
        return ResponseEntity.ok(ResponseUtil.success( formationService.getAllFormations()).getData());
    }

    // ✅ Récupérer une formation par ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getFormationById(@PathVariable Long id) {
        return formationService.getFormationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Récupérer une formation par nom
    @GetMapping("/nom/{nom}")
    public ResponseEntity<?> getFormationByNom(@PathVariable String nom) {
        return formationService.getFormationByNom(nom)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Vérifier si une formation existe par nom
    @GetMapping("/exists/{nom}")
    public ResponseEntity<Boolean> checkIfFormationExists(@PathVariable String nom) {
        return ResponseEntity.ok(ResponseUtil.success( formationService.formationExiste(nom)).getData());
    }

    // ✅ Récupérer les formations par responsable
    @GetMapping("/responsable/{responsableId}")
    public ResponseEntity<List<Formation>> getFormationsByResponsable(@PathVariable Long responsableId) {
        return ResponseEntity.ok(ResponseUtil.success( formationService.getFormationsParResponsable(responsableId)).getData());
    }

    // ✅ Créer une formation
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Formation> createFormation(@RequestBody Formation formation) {
        return ResponseEntity.ok(ResponseUtil.success("Formation crée avec succes", formationService.createFormation(formation)).getData());
    }

    // ✅ Modifier une formation (sauf responsable)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFormation(@PathVariable Long id, @RequestBody Formation formation) {
        try {
            return ResponseEntity.ok(ResponseUtil.success("Formation mise à jour avec succes" ,formationService.updateFormation(id, formation)).getData());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ Supprimer une formation
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFormation(@PathVariable Long id) {
        formationService.deleteFormation(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Assigner un responsable à une formation
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{formationId}/assigner-responsable/{enseignantId}")
    public ResponseEntity<?> assignerResponsable(@PathVariable Long formationId, @PathVariable Long enseignantId) {
        try {
            Formation formation = formationService.assignerResponsable(formationId, enseignantId);
            return ResponseEntity.ok(ResponseUtil.success( "Formation assognée avec succes",formation));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseUtil.error( e.getMessage()));
        }
    }
}
