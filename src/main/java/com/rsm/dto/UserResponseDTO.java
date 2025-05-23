package com.rsm.dto;

import com.rsm.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String matricule;
    private Role role;
}
