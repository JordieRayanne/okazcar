package com.okazcar.okazcar.services;

import com.okazcar.okazcar.models.Annonce;
import com.okazcar.okazcar.models.ClientVoiture;
import com.okazcar.okazcar.models.Utilisateur;
import com.okazcar.okazcar.models.file.AnnonceFile;
import com.okazcar.okazcar.repositories.AnnonceRepository;
import com.okazcar.okazcar.repositories.ClientVoitureRepository;
import com.okazcar.okazcar.services.file.AnnonceFileService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AnnoncePopulaireService {
    final AnnonceRepository annonceRepository;
    final AnnonceFileService annonceFileService;
    final ClientVoitureRepository clientVoitureRepository;
    final UtilisateurService utilisateurService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public AnnoncePopulaireService (AnnonceRepository annonceRepository, AnnonceFileService annonceFileService, ClientVoitureRepository clientVoitureRepository, UtilisateurService utilisateurService) {
        this.annonceRepository = annonceRepository;
        this.clientVoitureRepository = clientVoitureRepository;
        this.annonceFileService = annonceFileService;
        this.utilisateurService = utilisateurService;
    }

    public List<Annonce> getAnnoncesPopulaires() {
       List<Annonce> annonces = annonceRepository.findAll();
        List<Annonce> toReturn = new ArrayList<>();
        List<AnnonceFile> annonceFiles = annonceFileService.getAnnonceFiles();
        annonceFiles = annonceFiles.stream().sorted(Comparator.comparing(AnnonceFile::getTotalCount)).toList();
        Optional<Annonce> annonceOptional;
        for (AnnonceFile annonceFile: annonceFiles) {
            annonceOptional = annonces.stream().filter(annonce -> (annonce.getId()==annonceFile.getArticleId())).findFirst();
            annonceOptional.ifPresent(toReturn::add);
        }
        return toReturn;
    }

    @SuppressWarnings("unchecked")
    public List<ClientVoiture> getAnnoncesFavoris(HttpServletRequest request) {
        Utilisateur utilisateur = utilisateurService.extractUtilisateurFromHttpServletRequest(request);
        return (List<ClientVoiture>) entityManager.createNativeQuery("SELECT * FROM client_voiture WHERE favori = 10 AND id_utilisateur='"+utilisateur.getUtilisateurId()+"'", ClientVoiture.class).getResultList();
    }
}
