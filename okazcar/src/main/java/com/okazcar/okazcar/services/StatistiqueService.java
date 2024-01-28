package com.okazcar.okazcar.services;

import com.okazcar.okazcar.models.Statistique;
import com.okazcar.okazcar.repositories.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;

@Service
public class StatistiqueService {
    @PersistenceContext
    private EntityManager entityManager;
    private final V_Annonce_Repository v_Annonce_Repository;
    private final CategorieRepository categorieRepository;
    private final TypeRepository typeRepository;
    private final MarqueRepository marqueRepository;
    private final ModeleRepository modeleRepository;

    private final int year = LocalDate.now().getYear();

    @Autowired
    public StatistiqueService(V_Annonce_Repository v_Annonce_Repository,
                              AnnonceRepository annonceRepository,
                              CategorieRepository categorieRepository,
                              TypeRepository typeRepository,
                              MarqueRepository marqueRepository,
                              ModeleRepository modeleRepository) {
        this.v_Annonce_Repository = v_Annonce_Repository;
        this.categorieRepository = categorieRepository;
        this.typeRepository = typeRepository;
        this.marqueRepository = marqueRepository;
        this.modeleRepository = modeleRepository;
    }

    private HashMap<String, Long> getCardsData() {
        HashMap<String, Long> toReturn = new HashMap<>();
        long countAnnonceNonVendu =  v_Annonce_Repository.countV_AnnoncesByStatus(0);
        long countAnnonceVendu =  v_Annonce_Repository.countV_AnnoncesByStatus(10);
        String sql = "SELECT COUNT(*) FROM utilisateurs_role WHERE role_id_users_role=1";
        long countClient = (Long) entityManager.createNativeQuery(sql, Long.class).getSingleResult();
        String sql2 = "SELECT SUM(a.prix_commission) FROM annonce a WHERE a.status=10";
        Double sum = (Double) entityManager.createNativeQuery(sql2, Double.class).getSingleResult();
        long capitalTotal = sum != null ? sum.longValue() : 0;
        //
        toReturn.put("Annonces non vendues", countAnnonceNonVendu);
        toReturn.put("Annonces vendues", countAnnonceVendu);
        toReturn.put("Nombres de clients total", countClient);
        toReturn.put("Revenues totals", capitalTotal);
        return toReturn;
    }

    private long getTotalRevenues(int month) {
        return entityManager.createNativeQuery("SELECT SUM(a.prix_commission) FROM annonce a WHERE EXTRACT(YEAR from a.date_vente)="+year+" AND extract(Month FROM a.date_vente)="+month).getFirstResult();
    }

    private HashMap<String, Long> getRevenuesTotalMois() {
        HashMap<String, Long> toReturn = new HashMap<>();
        toReturn.put("January", getTotalRevenues(1));
        toReturn.put("February", getTotalRevenues(2));
        toReturn.put("March", getTotalRevenues(3));
        toReturn.put("April", getTotalRevenues(4));
        toReturn.put("May", getTotalRevenues(5));
        toReturn.put("June", getTotalRevenues(6));
        toReturn.put("July", getTotalRevenues(7));
        toReturn.put("August", getTotalRevenues(8));
        toReturn.put("September", getTotalRevenues(9));
        toReturn.put("October", getTotalRevenues(10));
        toReturn.put("November", getTotalRevenues(11));
        toReturn.put("December", getTotalRevenues(12));
        return toReturn;
    }

    private HashMap<String, Long> getParametre() {
        HashMap<String, Long> toReturn = new HashMap<>();
        toReturn.put("Categories", categorieRepository.count());
        toReturn.put("Types", typeRepository.count());
        toReturn.put("Marques", marqueRepository.count());
        toReturn.put("Modeles", modeleRepository.count());
        return toReturn;
    }

    public Statistique init() {
        return new Statistique(getCardsData(), getRevenuesTotalMois(), getParametre());
    }
}
