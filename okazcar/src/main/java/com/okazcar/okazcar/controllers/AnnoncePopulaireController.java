package com.okazcar.okazcar.controllers;

import com.okazcar.okazcar.models.Annonce;
import com.okazcar.okazcar.repositories.AnnonceRepository;
import com.okazcar.okazcar.services.AnnoncePopulaireService;
import com.okazcar.okazcar.services.AnnonceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AnnoncePopulaireController {
    final AnnoncePopulaireService annoncePopulaireService;
    final AnnonceRepository annonceRepository;
    final AnnonceService annonceService;

    public AnnoncePopulaireController(AnnoncePopulaireService annoncePopulaireService, AnnonceRepository annonceRepository, AnnonceService annonceService) {
        this.annoncePopulaireService = annoncePopulaireService;
        this.annonceService = annonceService;
        this.annonceRepository = annonceRepository;
    }

    @GetMapping("/anonnces_populaire")
    public ResponseEntity<List<Annonce>> getAnnoncePopulaires() {
        return new ResponseEntity<>(annoncePopulaireService.getAnnoncesPopulaires(), HttpStatus.OK);
    }

    @GetMapping("/anonnces_favori")
    public ResponseEntity<List<Annonce>> getAnnonceFavoris(HttpServletRequest request) {
        List<Annonce> annonces = annoncePopulaireService.getAnnoncesFavoris(request);
        for (int i = 0; i < annonces.size(); i++) {
            annonces.get(i).getVoitureUtilisateur().getUtilisateur().setRoles(null);
            annonces.get(i).getVoitureUtilisateur().getUtilisateur().setPassword(null);
        }
        return new ResponseEntity<>(annonces, HttpStatus.OK);
    }
}
