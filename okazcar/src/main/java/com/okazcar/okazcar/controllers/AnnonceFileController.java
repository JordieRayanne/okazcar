package com.okazcar.okazcar.controllers;

import com.okazcar.okazcar.models.Utilisateur;
import com.okazcar.okazcar.models.dto.AnnonceFileDto;
import com.okazcar.okazcar.services.UtilisateurService;
import com.okazcar.okazcar.services.file.AnnonceFileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class AnnonceFileController {
    private final AnnonceFileService articleFileService;
    private final UtilisateurService utilisateurService;
    @Autowired
    public AnnonceFileController(AnnonceFileService articleFileService, UtilisateurService utilisateurService) {
       this.articleFileService = articleFileService;
       this.utilisateurService = utilisateurService;
    }

    @GetMapping("/historic/{annonceId}")
    // @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<String> writeArticleFile(@PathVariable("annonceId") int annonceId, HttpServletRequest request) {
        Utilisateur utilisateur = utilisateurService.extractUtilisateurFromHttpServletRequest(request);
        AnnonceFileDto annonceFileDto = new AnnonceFileDto(annonceId, utilisateur.getUtilisateurId());
        try {
            articleFileService.writeFile(annonceFileDto.getArticleFile());
            return new ResponseEntity<>("Writed", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
