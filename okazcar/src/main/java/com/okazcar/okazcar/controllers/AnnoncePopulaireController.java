package com.okazcar.okazcar.controllers;

import com.okazcar.okazcar.models.Annonce;
import com.okazcar.okazcar.services.AnnoncePopulaireService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AnnoncePopulaireController {
    final AnnoncePopulaireService annoncePopulaireService;

    public AnnoncePopulaireController(AnnoncePopulaireService annoncePopulaireService) {
        this.annoncePopulaireService = annoncePopulaireService;
    }

    @GetMapping("/anonnces/populaire")
    public ResponseEntity<List<Annonce>> getAnnoncePopulaires() {
        return new ResponseEntity<>(annoncePopulaireService.getAnnoncesPopulaires(), HttpStatus.OK);
    }
}
