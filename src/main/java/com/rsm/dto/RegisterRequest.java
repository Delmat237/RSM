package com.rsm.dto;

import lombok.Data;
import com.rsm.entity.Role;

@Data
public class RegisterRequest {
    private String nom;
    private String prenom;
    private String email;
    private String matricule;
    private String motDePasse;
    private Role role;
}
