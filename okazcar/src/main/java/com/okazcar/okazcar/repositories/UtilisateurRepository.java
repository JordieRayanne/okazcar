package com.okazcar.okazcar.repositories;

import com.okazcar.okazcar.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, String> {
    Utilisateur findUtilisateurByEmail(String email);
    Utilisateur findUtilisateurByUtilisateurId(String utilisateurId);
}
