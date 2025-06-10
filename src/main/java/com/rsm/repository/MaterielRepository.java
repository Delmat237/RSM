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

}
