package com.rsm.dto;

import com.rsm.entity.Materiel;
import com.rsm.entity.Salle;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
public class ReservationDto {
    private Long id;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private Date heureDebut;
    private Date heureFin;
    private String motif;
    private Salle salle;
    private Set<Materiel> materiels;
}
