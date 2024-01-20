package com.okazcar.okazcar.controllers;

import com.okazcar.okazcar.models.Annonce;
import com.okazcar.okazcar.repositories.AnnonceRepository;
import com.okazcar.okazcar.services.AnnoncePopulaireService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AnnoncePopulaireController {
    final AnnoncePopulaireService annoncePopulaireService;
    final AnnonceRepository annonceRepository;

    public AnnoncePopulaireController(AnnoncePopulaireService annoncePopulaireService, AnnonceRepository annonceRepository) {
        this.annoncePopulaireService = annoncePopulaireService;
        this.annonceRepository = annonceRepository;
    }

    @GetMapping("/anonnces/populaire")
    public ResponseEntity<List<Annonce>> getAnnoncePopulaires() {
        return new ResponseEntity<>(annoncePopulaireService.getAnnoncesPopulaires(), HttpStatus.OK);
    }

    @GetMapping("/anonnces/favori")
    public ResponseEntity<List<Annonce>> getAnnonceFavoris() {
        return new ResponseEntity<>(annonceRepository.findAnnoncesByStatus(10), HttpStatus.OK);
    }
}
