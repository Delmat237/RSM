package com.rsm.repository;



import com.rsm.entity.Materiel;
import com.rsm.entity.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Repository
public interface SalleRepository extends JpaRepository<Salle, Long> {

}
