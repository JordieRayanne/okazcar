package com.okazcar.okazcar.repositories;

import com.okazcar.okazcar.models.V_Annonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface V_Annonce_Repository extends JpaRepository<V_Annonce, Integer> {
    List<V_Annonce> findV_AnnoncesByCategorieAndMarqueAndTypeAndDateCreationAndLocalisationAndPrix(
            String categorie,
            String marque,
            String type,
            Timestamp dateCreation,
            String localisation,
            double prix
    );

    List<V_Annonce> findV_AnnoncesByVoitureUtilisateurEtat(int voitureUtilisateurEtat);
}
