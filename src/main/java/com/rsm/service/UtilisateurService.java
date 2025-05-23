package com.rsm.service;

import com.rsm.entity.Utilisateur;
import com.rsm.repository.EnseignantRepository;
import com.rsm.repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private EtudiantRepository etudiantRepository;


    public Optional<? extends Utilisateur> findByEmail(String email) {
        Optional<? extends Utilisateur> user;


        user = enseignantRepository.findByEmail(email);
        if (user.isPresent()) return user;

        user = etudiantRepository.findByEmail(email);
        return user;
    }
}
