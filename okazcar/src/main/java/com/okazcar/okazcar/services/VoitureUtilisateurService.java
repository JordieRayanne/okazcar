package com.okazcar.okazcar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.okazcar.okazcar.models.VoitureUtilisateur;
import com.okazcar.okazcar.repositories.VoitureUtilisateurRepository;

@Service
public class VoitureUtilisateurService {
    private final VoitureUtilisateurRepository voitureUtilisateurRepository;
    
    @Autowired
    public VoitureUtilisateurService(VoitureUtilisateurRepository voitureUtilisateurRepository){
        this.voitureUtilisateurRepository=voitureUtilisateurRepository;
    }

    public List<VoitureUtilisateur> getAllVoitureUtilisateurs(){
        return voitureUtilisateurRepository.findAll();
    }

    public VoitureUtilisateur createVoitureUtilisateur(VoitureUtilisateur voitureUtilisateur){
        return voitureUtilisateurRepository.save(voitureUtilisateur);
    }

    public VoitureUtilisateur updateVoitureUtilisateur(VoitureUtilisateur voitureUtilisateur){
        return voitureUtilisateurRepository.save(voitureUtilisateur);
    }

    public VoitureUtilisateur voitureUtilisateurEtatTo10(int id) {
        VoitureUtilisateur voitureUtilisateur = voitureUtilisateurRepository.findById(id).orElseThrow(() -> new RuntimeException("VoitureUtilisateur not found"));
        voitureUtilisateur.setEtat(10);
        return voitureUtilisateurRepository.save(voitureUtilisateur);
    }
}
