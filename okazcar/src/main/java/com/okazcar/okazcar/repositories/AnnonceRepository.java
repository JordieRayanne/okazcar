package com.okazcar.okazcar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.okazcar.okazcar.models.Annonce;


@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Integer> {

    Annonce findAnnonceById(int id);

    @Query("SELECT SUM(a.prixCommission) FROM annonce a where EXTRACT(YEAR from a.dateVente) = ?1 AND EXTRACT(month FROM a.dateVente) = ?2")
    long getTotalRevenuesTotal(int year, int month);
}
