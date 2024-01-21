package com.okazcar.okazcar.repositories;

import com.okazcar.okazcar.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.okazcar.okazcar.models.ClientVoiture;

import java.util.List;

@Repository
public interface ClientVoitureRepository extends JpaRepository<ClientVoiture,Integer> {
    List<ClientVoiture> findClientVoituresByFavoriAndUtilisateur(int favori, Utilisateur utilisateur);
}
