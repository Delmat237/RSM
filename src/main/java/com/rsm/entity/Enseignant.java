package com.rsm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Enseignant extends Utilisateur {
    @OneToMany(mappedBy = "enseignant")
    @JsonIgnore
    private List<Reservation> reservations;
}
