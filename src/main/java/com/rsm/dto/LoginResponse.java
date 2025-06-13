package com.rsm.dto;

import lombok.Data;
import com.rsm.entity.Enseignant;

@Data
public class LoginResponse {
    private String token;
    private Enseignant enseignant;

    public LoginResponse(String token,Enseignant enseignant) {
        this.token = token;
        this.enseignant = enseignant;
    }
}

