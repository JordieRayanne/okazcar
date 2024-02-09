package com.okazcar.okazcar.controllers;

import com.okazcar.okazcar.models.AnnonceImage;
import com.okazcar.okazcar.models.ClientVoitureImage;
import com.okazcar.okazcar.repositories.AnnonceRepository;
import com.okazcar.okazcar.services.AnnoncePopulaireService;
import com.okazcar.okazcar.services.AnnonceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/annonces_populaire")
    public ResponseEntity<List<AnnonceImage>> getAnnoncePopulaires() {
        return new ResponseEntity<>(annoncePopulaireService.getAnnoncesPopulaires(), HttpStatus.OK);
    }

    @GetMapping("/annonces_favori")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<ClientVoitureImage>> getAnnonceFavoris(HttpServletRequest request) {
        List<ClientVoitureImage> annonces = annoncePopulaireService.getAnnoncesFavoris(request);
        return new ResponseEntity<>(annonces, HttpStatus.OK);
    }
}
