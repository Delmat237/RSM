package reservation.rsm.controller;


import reservation.rsm.dto.AuthRequestDto;
import reservation.rsm.dto.AuthResponseDto;
import reservation.rsm.service.JwtService;
import reservation.rsm.service.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UtilisateurService utilisateurService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthRequestDto request) {
        try {
            // Authentifier l’utilisateur avec username et password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // Charger les détails utilisateur
            UserDetails userDetails = utilisateurService.loadUserByUsername(request.getUsername());

            // Générer le token JWT
            String token = jwtService.generateToken(userDetails.getUsername());

            // Retourner le token dans la réponse
            return ResponseEntity.ok(new AuthResponseDto(token));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants invalides");
        }
    }
}
