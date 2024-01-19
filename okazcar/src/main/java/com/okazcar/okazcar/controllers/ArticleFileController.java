package com.okazcar.okazcar.controllers;

import com.okazcar.okazcar.models.Utilisateur;
import com.okazcar.okazcar.models.dto.ArticleFileDto;
import com.okazcar.okazcar.services.UtilisateurService;
import com.okazcar.okazcar.services.file.ArticleFileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class ArticleFileController {
    private final ArticleFileService articleFileService;
    private final UtilisateurService utilisateurService;
    @Autowired
    public ArticleFileController(ArticleFileService articleFileService, UtilisateurService utilisateurService) {
       this.articleFileService = articleFileService;
       this.utilisateurService = utilisateurService;
    }

    @GetMapping("/historic/{articleId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<String> writeArticleFile(@PathVariable("articleId") int articleId, HttpServletRequest request) {
        Utilisateur utilisateur = utilisateurService.extractUtilisateurFromHttpServletRequest(request);
        ArticleFileDto articleFileDto = new ArticleFileDto(articleId, utilisateur.getUtilisateurId());
        try {
            articleFileService.writeFile(articleFileDto.getArticleFile());
            return new ResponseEntity<>("Writed", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
