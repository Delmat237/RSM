package com.rsm.service;

import com.rsm.entity.Materiel;
import com.rsm.repository.MaterielRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterielService {

    private final MaterielRepository materielRepository;

    @Autowired
    public MaterielService(MaterielRepository materielRepository) {
        this.materielRepository = materielRepository;
    }

    /**
     * Retourne la liste de tous les matériels.
     */
    public List<Materiel> getAllMateriels() {
        return materielRepository.findAll();
    }

    /**
     * Recherche un matériel par son identifiant.
     */
    public Optional<Materiel> getMaterielById(Long id) {
        return materielRepository.findById(id);
    }

    /**
     * Crée un nouveau matériel.
     */
    public Materiel createMateriel(Materiel materiel) {
        return materielRepository.save(materiel);
    }

    /**
     * Met à jour un matériel existant.
     * @throws Exception si le matériel n'existe pas.
     */
    public Materiel updateMateriel(Long id, Materiel materielDetails) throws Exception {
        Materiel materiel = materielRepository.findById(id)
                .orElseThrow(() -> new Exception("Matériel non trouvé avec l'id : " + id));

        materiel.setType(materielDetails.getType());
        materiel.setDisponible(materielDetails.isDisponible());

        return materielRepository.save(materiel);
    }

    /**
     * Supprime un matériel par son identifiant.
     * @throws Exception si le matériel n'existe pas.
     */
    public void deleteMateriel(Long id) throws Exception {
        Materiel materiel = materielRepository.findById(id)
                .orElseThrow(() -> new Exception("Matériel non trouvé avec l'id : " + id));
        materielRepository.delete(materiel);
    }
}
