package com.okazcar.okazcar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.okazcar.okazcar.models.VoitureUtilisateur;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoitureUtilisateurRepository extends JpaRepository<VoitureUtilisateur,Integer>{
    List<VoitureUtilisateur> findVoitureUtilisateursByEtat(int etat);
    @Query(value = "SELECT v FROM voiture_utilisateur v WHERE v.etat=?1 AND v.utilisateur.utilisateurId=?2")
    List<VoitureUtilisateur> getMesVoituresUtilisateurs(int etat, String utilisateurId);
}
