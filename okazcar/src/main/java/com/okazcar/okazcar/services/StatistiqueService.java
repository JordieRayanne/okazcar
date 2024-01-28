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
    private final AnnonceRepository annonceRepository;
    private final CategorieRepository categorieRepository;
    private final TypeRepository typeRepository;
    private final MarqueRepository marqueRepository;
    private final ModeleRepository modeleRepository;

    @Autowired
    public StatistiqueService(V_Annonce_Repository v_Annonce_Repository,
                              AnnonceRepository annonceRepository,
                              CategorieRepository categorieRepository,
                              TypeRepository typeRepository,
                              MarqueRepository marqueRepository,
                              ModeleRepository modeleRepository) {
        this.v_Annonce_Repository = v_Annonce_Repository;
        this.annonceRepository = annonceRepository;
        this.categorieRepository = categorieRepository;
        this.typeRepository = typeRepository;
        this.marqueRepository = marqueRepository;
        this.modeleRepository = modeleRepository;
    }

    private HashMap<String, Long> getCardsData() {
        HashMap<String, Long> toReturn = new HashMap<>();
        long countAnnonceNonVendu =  v_Annonce_Repository.countV_AnnoncesByStatus(0);
        long countAnnonceVendu =  v_Annonce_Repository.countV_AnnoncesByStatus(10);
        String sql = "SELECT COUNT(ur) FROM utilisateurs_role ur WHERE ur.role_id_users_role="+1;
        Query query = entityManager.createNativeQuery(sql);
        long countClient = query.getFirstResult();
        String sql2 = "SELECT SUM(a.prix_commission) FROM annonce a WHERE a.status="+10;
        Query query2 = entityManager.createNativeQuery(sql2);
        long capitalTotal = query2.getFirstResult();
        //
        toReturn.put("Annonces non vendues", countAnnonceNonVendu);
        toReturn.put("Annonces vendues", countAnnonceVendu);
        toReturn.put("Nombres de clients total", countClient);
        toReturn.put("Revenues totals", capitalTotal);
        return toReturn;
    }

    private HashMap<String, Long> getRevenuesTotalMois() {
        HashMap<String, Long> toReturn = new HashMap<>();
        toReturn.put("January", annonceRepository.getTotalRevenuesTotal(LocalDate.now().getYear(), 1));
        toReturn.put("February", annonceRepository.getTotalRevenuesTotal(LocalDate.now().getYear(), 2));
        toReturn.put("March", annonceRepository.getTotalRevenuesTotal(LocalDate.now().getYear(), 3));
        toReturn.put("April", annonceRepository.getTotalRevenuesTotal(LocalDate.now().getYear(), 4));
        toReturn.put("May", annonceRepository.getTotalRevenuesTotal(LocalDate.now().getYear(), 5));
        toReturn.put("June", annonceRepository.getTotalRevenuesTotal(LocalDate.now().getYear(), 6));
        toReturn.put("July", annonceRepository.getTotalRevenuesTotal(LocalDate.now().getYear(), 7));
        toReturn.put("August", annonceRepository.getTotalRevenuesTotal(LocalDate.now().getYear(), 8));
        toReturn.put("September", annonceRepository.getTotalRevenuesTotal(LocalDate.now().getYear(), 9));
        toReturn.put("October", annonceRepository.getTotalRevenuesTotal(LocalDate.now().getYear(), 10));
        toReturn.put("November", annonceRepository.getTotalRevenuesTotal(LocalDate.now().getYear(), 11));
        toReturn.put("December", annonceRepository.getTotalRevenuesTotal(LocalDate.now().getYear(), 12));
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
