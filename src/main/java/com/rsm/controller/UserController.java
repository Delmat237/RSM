package com.rsm.controller;

import com.rsm.dto.UserResponseDTO;

import com.rsm.payload.ApiResponse;
import com.rsm.service.UtilisateurService;
import com.rsm.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return utilisateurService.findByEmail(userDetails.getUsername())
                .map(user -> {
                    UserResponseDTO dto = new UserResponseDTO(
                            user.getId(), user.getNom(), user.getPrenom(),
                            user.getEmail(), user.getMatricule(), user.getRole()
                    );
                    return ResponseEntity.ok(ResponseUtil.success("Utilisateur récupéré avec succès", dto));
                })
                .orElseGet(() ->
                        ResponseEntity.status(404).body(ResponseUtil.error("Utilisateur non trouvé"))
                );
    }
}
