package reservation.rsm.controller;


import reservation.rsm.entity.Salle;
import reservation.rsm.service.SalleService;
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

    // Tout le monde peut consulter la liste des salles
    @GetMapping
    public ResponseEntity<List<Salle>> getAllSalles() {
        return ResponseEntity.ok(salleService.getAllSalles());
    }

    // Consultation d'une salle par id
    @GetMapping("/{id}")
    public ResponseEntity<?> getSalleById(@PathVariable Long id) {
        return salleService.getSalleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Création d'une salle - accès réservé à l'administrateur
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Salle> createSalle(@RequestBody Salle salle) {
        Salle nouvelleSalle = salleService.createSalle(salle);
        return ResponseEntity.ok(nouvelleSalle);
    }

    // Mise à jour d'une salle - accès réservé à l'administrateur
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSalle(@PathVariable Long id, @RequestBody Salle salle) {
        try {
            Salle updated = salleService.updateSalle(id, salle);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Suppression d'une salle - accès réservé à l'administrateur
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSalle(@PathVariable Long id) {
        try {
            salleService.deleteSalle(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
