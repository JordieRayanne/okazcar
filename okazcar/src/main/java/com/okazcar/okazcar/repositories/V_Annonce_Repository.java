package com.okazcar.okazcar.repositories;

import com.okazcar.okazcar.models.V_Annonce;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface V_Annonce_Repository extends JpaRepository<V_Annonce, Integer> {

    // @Query("SELECT v FROM v_annonce v " +
    // "WHERE LOWER(v.categorie) LIKE LOWER(CONCAT('%', :categorie, '%')) " +
    // "AND LOWER(v.marque) LIKE LOWER(CONCAT('%', :marque, '%')) " +
    // "AND LOWER(v.type) LIKE LOWER(CONCAT('%', :type, '%')) " +
    // "AND v.date_creation LIKE CONCAT('%', :date_creation, '%') " +
    // "AND LOWER(v.localisation) LIKE LOWER(CONCAT('%', :localisation, '%')) " +
    // "AND v.prix LIKE CONCAT('%', :prix, '%')")
    // List<V_Annonce> findByFilters(
    //         @Param("categorie") String categorie,
    //         @Param("marque") String marque,
    //         @Param("type") String type,
    //         @Param("dateCreation") String dateCreation,
    //         @Param("localisation") String localisation,
    //         @Param("prix") String prix
    // );
    

    
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
