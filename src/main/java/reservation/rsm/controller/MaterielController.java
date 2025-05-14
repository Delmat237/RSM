package reservation.rsm.controller;

import reservation.rsm.entity.Materiel;
import reservation.rsm.service.MaterielService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materiels")
public class MaterielController {

    private final MaterielService materielService;

    public MaterielController(MaterielService materielService) {
        this.materielService = materielService;
    }

    @GetMapping
    public ResponseEntity<List<Materiel>> getAllMateriels() {
        return ResponseEntity.ok(materielService.getAllMateriels());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMaterielById(@PathVariable Long id) {
        return materielService.getMaterielById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Materiel> createMateriel(@RequestBody Materiel materiel) {
        return ResponseEntity.ok(materielService.createMateriel(materiel));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMateriel(@PathVariable Long id, @RequestBody Materiel materiel) {
        try {
            return ResponseEntity.ok(materielService.updateMateriel(id, materiel));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMateriel(@PathVariable Long id) {
        try {
            materielService.deleteMateriel(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
