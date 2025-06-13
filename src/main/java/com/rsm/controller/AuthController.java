package com.rsm.controller;

import com.rsm.dto.LoginRequest;
import com.rsm.dto.LoginResponse;
import com.rsm.dto.RegisterRequest;
import com.rsm.entity.Enseignant;
import com.rsm.entity.Etudiant;
import com.rsm.entity.Role;
import com.rsm.payload.ApiResponse;
import com.rsm.repository.EnseignantRepository;
import com.rsm.repository.EtudiantRepository;
import com.rsm.security.JwtUtils;
import com.rsm.util.ResponseUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentification", description = "Gestion de l'authentification")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EnseignantRepository enseignantRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getMotDePasse()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtils.generateToken(userDetails);
        Enseignant enseignant = enseignantRepository.findByEmail(request.getEmail()).orElse(null);
        return ResponseEntity.ok(ResponseUtil.success("Connexion réussie", new LoginResponse(token,enseignant)));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@RequestBody RegisterRequest request) {
        if (request.getRole().equals(Role.ENSEIGNANT)) {
            Optional<Enseignant> existing = enseignantRepository.findByEmail(request.getEmail());
            if (existing.isPresent()) {
                return ResponseEntity.badRequest().body(ResponseUtil.error("Email déjà utilisé"));
            }

            Enseignant enseignant = new Enseignant();
            enseignant.setNom(request.getNom());
            enseignant.setPrenom(request.getPrenom());
            enseignant.setEmail(request.getEmail());
            enseignant.setMatricule(request.getMatricule());
            enseignant.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
            enseignant.setRole(request.getRole());

            enseignantRepository.save(enseignant);
            return ResponseEntity.ok(ResponseUtil.success("Enseignant enregistré avec succès", null));

        } else if (request.getRole().equals(Role.ETUDIANT)) {
            Optional<Etudiant> existing = etudiantRepository.findByEmail(request.getEmail());
            if (existing.isPresent()) {
                return ResponseEntity.badRequest().body(ResponseUtil.error("Email déjà utilisé"));
            }

            Etudiant etudiant = new Etudiant();
            etudiant.setNom(request.getNom());
            etudiant.setPrenom(request.getPrenom());
            etudiant.setEmail(request.getEmail());
            etudiant.setMatricule(request.getMatricule());
            etudiant.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
            etudiant.setRole(request.getRole());

            etudiantRepository.save(etudiant);
            return ResponseEntity.ok(ResponseUtil.success("Etudiant enregistré avec succès", null));
        }

        return ResponseEntity.badRequest().body(ResponseUtil.error("Rôle non pris en charge"));
    }
}
