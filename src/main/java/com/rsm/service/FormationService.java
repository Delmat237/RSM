package com.rsm.service;

import com.rsm.entity.Enseignant;
import com.rsm.entity.Formation;
import com.rsm.repository.EnseignantRepository;
import com.rsm.repository.FormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormationService {

    @Autowired
    private FormationRepository formationRepository;

    @Autowired
    private EnseignantRepository enseignantRepository;

    // Obtenir toutes les formations
    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }

    // Récupérer une formation par son ID
    public Optional<Formation> getFormationById(Long id) {
        return formationRepository.findById(id);
    }

    // Créer une nouvelle formation
    public Formation createFormation(Formation formation) {
        return formationRepository.save(formation);
    }

    // Mettre à jour une formation
    public Formation updateFormation(Long id, Formation updatedFormation) throws Exception {
        Formation formation = formationRepository.findById(id)
                .orElseThrow(() -> new Exception("Formation non trouvée"));

        formation.setNom(updatedFormation.getNom());
        // Pour éviter les conflits, ne pas mettre à jour le responsable ici
        return formationRepository.save(formation);
    }

    // Supprimer une formation
    public void deleteFormation(Long id) {
        formationRepository.deleteById(id);
    }

    // Assigner un enseignant comme responsable d'une formation
    public Formation assignerResponsable(Long formationId, Long enseignantId) throws Exception {
        Formation formation = formationRepository.findById(formationId)
                .orElseThrow(() -> new Exception("Formation non trouvée"));

        Enseignant enseignant = enseignantRepository.findById(enseignantId)
                .orElseThrow(() -> new Exception("Enseignant non trouvé"));

        formation.setResponsable(enseignant);
        return formationRepository.save(formation);
    }

    // ➕ Méthodes personnalisées de FormationRepository :

    public Optional<Formation> getFormationByNom(String nom) {
        return formationRepository.findByNom(nom);
    }

    public boolean formationExiste(String nom) {
        return formationRepository.existsByNom(nom);
    }

    public List<Formation> getFormationsParResponsable(Long responsableId) {
        return formationRepository.findByResponsableId(responsableId);
    }
}
