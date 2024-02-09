package com.okazcar.okazcar.controllers;

import com.okazcar.okazcar.models.*;
import com.okazcar.okazcar.repositories.V_Annonce_Repository;

import com.okazcar.okazcar.repositories.mongodb.VoitureImageRepository;
import com.okazcar.okazcar.services.UtilisateurService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@RestController
public class V_Annonce_Controller {

    private final V_Annonce_Repository v_AnnonceRepository;
    final UtilisateurService utilisateurService;
    private final VoitureImageRepository voitureImageRepository;

    @Autowired
    public V_Annonce_Controller(V_Annonce_Repository v_AnnonceRepository, UtilisateurService utilisateurService,
                                VoitureImageRepository voitureImageRepository) {
        this.v_AnnonceRepository = v_AnnonceRepository;
        this.utilisateurService = utilisateurService;
        this.voitureImageRepository = voitureImageRepository;
    }

    @GetMapping("/v_annonces")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<V_Annonce> getAll() {
        return v_AnnonceRepository.findAll();
    }

    @GetMapping("/v_annonces/{id_annonce}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<V_Annonce> getById(@PathVariable("id_annonce") Integer id_annonce) {
        Optional<V_Annonce> v_AnnonceOptional = v_AnnonceRepository.findById(id_annonce);
        return v_AnnonceOptional.map(v_Annonce -> new ResponseEntity<>(v_Annonce, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/v_annonces_search")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<V_Annonce> getFilteredVAnnonces(
            @RequestParam(required = false, defaultValue = "") String categorie,
            @RequestParam(required = false, defaultValue = "") String modele,
            @RequestParam(required = false, defaultValue = "") String type,
            @RequestParam(required = false, defaultValue = "") String localisation,
            @RequestParam(required = false, defaultValue = "") String couleur
    ) {
        return v_AnnonceRepository.findV_AnnoncesByCategorieAndModeleAndTypeAndLocalisationAndCouleur(categorie,modele,type,localisation,couleur);
    }

    @GetMapping("/v_annonces_vendu")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<V_Annonce> getFilteredVAnnoncesVendu() {
        try {
            return v_AnnonceRepository.findV_AnnoncesByStatus(10);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @GetMapping("/v_annonces_non_vendu")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<V_Annonce> getFilteredVAnnoncesNonVendu() {
        try {
            return v_AnnonceRepository.findV_AnnoncesByStatus(0);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @GetMapping("/annonces_non_vendu_img")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getMesAnnoncesNonVendusImg(HttpServletRequest request) {
        try {
            Utilisateur utilisateur = utilisateurService.extractUtilisateurFromHttpServletRequest(request);
            List<V_Annonce> vAnnonces = v_AnnonceRepository.findV_AnnoncesByStatusAndIdUtilisateur(0, utilisateur.getUtilisateurId());
            List<VAnnonceImage> vAnnonceImages = new ArrayList<>();
            for (V_Annonce vAnnonce: vAnnonces) {
                vAnnonceImages.add(new VAnnonceImage(vAnnonce, voitureImageRepository.findVoitureImageByVoitureId(vAnnonce.getIdVoiture())));
            }
            return new ResponseEntity<>(vAnnonceImages, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/annonces_non_vendu")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getMesAnnoncesNonVendus(HttpServletRequest request) {
        try {
            Utilisateur utilisateur = utilisateurService.extractUtilisateurFromHttpServletRequest(request);
            return new ResponseEntity<>(v_AnnonceRepository.findV_AnnoncesByStatusAndIdUtilisateur(0, utilisateur.getUtilisateurId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/annonces_vendu")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getMesAnnoncesVendus(HttpServletRequest request) {
        try {
            Utilisateur utilisateur = utilisateurService.extractUtilisateurFromHttpServletRequest(request);
            return new ResponseEntity<>(v_AnnonceRepository.findV_AnnoncesByStatusAndIdUtilisateur(10, utilisateur.getUtilisateurId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/annonces_vendu_img")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getMesAnnoncesVendusImg(HttpServletRequest request) {
        try {
            Utilisateur utilisateur = utilisateurService.extractUtilisateurFromHttpServletRequest(request);
            List<V_Annonce> vAnnonces = v_AnnonceRepository.findV_AnnoncesByStatusAndIdUtilisateur(10, utilisateur.getUtilisateurId());
            List<VAnnonceImage> vAnnonceImages = new ArrayList<>();
            for (V_Annonce vAnnonce: vAnnonces) {
                vAnnonceImages.add(new VAnnonceImage(vAnnonce, voitureImageRepository.findVoitureImageByVoitureId(vAnnonce.getIdVoiture())));
            }
            return new ResponseEntity<>(vAnnonceImages, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}