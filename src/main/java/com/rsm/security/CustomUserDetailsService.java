package com.rsm.security;


import com.rsm.entity.Utilisateur;
import com.rsm.repository.EnseignantRepository;
import com.rsm.repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Primary
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private EnseignantRepository enseignantRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur user;

        // Recherche dans les enseignants
        var enseignantOpt = enseignantRepository.findByEmail(email);
        if (enseignantOpt.isPresent()) {
            user = enseignantOpt.get();
        } else {
            // Sinon cherche dans les étudiants
            var etudiantOpt = etudiantRepository.findByEmail(email);
            if (etudiantOpt.isPresent()) {
                user = etudiantOpt.get();
            } else {
                throw new UsernameNotFoundException("Aucun utilisateur trouvé pour : " + email);
            }
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getMotDePasse(),
                getAuthorities(user)
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Utilisateur user) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }
}
