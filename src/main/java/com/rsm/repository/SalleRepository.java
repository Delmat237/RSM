package com.rsm.repository;



import com.rsm.entity.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalleRepository extends JpaRepository<Salle, Long> {
    // Méthodes supplémentaires personnalisées si besoin
}
