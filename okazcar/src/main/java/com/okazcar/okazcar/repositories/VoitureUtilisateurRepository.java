package com.okazcar.okazcar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.okazcar.okazcar.models.VoitureUtilisateur;

import java.util.List;

public interface VoitureUtilisateurRepository extends JpaRepository<VoitureUtilisateur,Integer>{
    List<VoitureUtilisateur> findVoitureUtilisateursByEtat(int etat);
}
