package com.rsm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.rsm.entity.Materiel;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface MaterielRepository extends JpaRepository<Materiel, Long> {
    public default Arrays findAllById(Set<Long> materielIds){
        return null;
    };
    public default List<Materiel> findAll() {
        return List.of();
    }

    public default Optional<Materiel> findById(Long id) {
        return Optional.empty();
    }

    public default Materiel save(Materiel materiel) {
        return materiel;
    }

    public default void delete(Materiel materiel) {
    }
}
