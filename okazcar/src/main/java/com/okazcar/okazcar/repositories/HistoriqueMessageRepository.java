package com.okazcar.okazcar.repositories;

import com.okazcar.okazcar.models.HistoriqueMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistoriqueMessageRepository extends JpaRepository<HistoriqueMessage, Integer> {

    @Query("SELECT hm FROM historique_message hm WHERE hm.utilisateurIdHistoriqueMessage1 = ?1 OR hm.utilisateurIdHistoriqueMessage2= ?1")
    List<HistoriqueMessage> findAllContacts(String utilisateur1);

    @Query("SELECT hm FROM historique_message hm WHERE (hm.utilisateurIdHistoriqueMessage1 = ?1 AND hm.utilisateurIdHistoriqueMessage2= ?2) OR (hm.utilisateurIdHistoriqueMessage1 = ?2 AND hm.utilisateurIdHistoriqueMessage2= ?1)")
    List<HistoriqueMessage> findHistoriqueMessagesFrom2Utilisateurs(String utilisateurId1, String utilisateurId2);
}
