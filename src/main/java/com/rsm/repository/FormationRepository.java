package com.rsm.repository;

import com.rsm.entity.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {

    // Recherche d'une formation par nom (exact)
    Optional<Formation> findByNom(String nom);

    // Vérifie si une formation existe par nom
    boolean existsByNom(String nom);

    // Liste des formations associées à un responsable donné
    List<Formation> findByResponsableId(Long responsableId);
}
