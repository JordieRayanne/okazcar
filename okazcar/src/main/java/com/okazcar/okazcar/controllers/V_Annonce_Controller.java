package com.okazcar.okazcar.controllers;

import com.okazcar.okazcar.models.*;
import com.okazcar.okazcar.repositories.V_Annonce_Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@RestController
public class V_Annonce_Controller {

    private final V_Annonce_Repository v_AnnonceRepository;

    @Autowired
    public V_Annonce_Controller(V_Annonce_Repository v_AnnonceRepository) {
        this.v_AnnonceRepository = v_AnnonceRepository;
    }

    @GetMapping("/v_annonce")
    public List<V_Annonce> getAll() {
        return v_AnnonceRepository.findAll();
    }

    @GetMapping("/v_annonce/{id_annonce}")
    public ResponseEntity<V_Annonce> getById(@PathVariable("id_annonce") Integer id_annonce) {
        Optional<V_Annonce> v_AnnonceOptional = v_AnnonceRepository.findById(id_annonce);
        return v_AnnonceOptional.map(v_Annonce -> new ResponseEntity<>(v_Annonce, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/v-annonces")
    public List<V_Annonce> getFilteredVAnnonces(
            @RequestParam(required = false, defaultValue = "") String categorie,
            @RequestParam(required = false, defaultValue = "") String modele,
            @RequestParam(required = false, defaultValue = "") String type,
            @RequestParam(required = false, defaultValue = "") String localisation,
            @RequestParam(required = false, defaultValue = "") String couleur
    ) throws ParseException {
        // parse manta
        //return null;
        return v_AnnonceRepository.findV_AnnoncesByCategorieAndModeleAndTypeAndLocalisationAndCouleur(categorie,modele,type,localisation,couleur);
    }

    @GetMapping("/v-annonces-valide")
    public List<V_Annonce> getFilteredVAnnoncesValide() {
        try {
            return v_AnnonceRepository.findV_AnnoncesByVoitureUtilisateurEtat(10);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @GetMapping("/v-annonces-non-valide")
    public List<V_Annonce> getFilteredVAnnoncesNonValide() {
        try {
            return v_AnnonceRepository.findV_AnnoncesByVoitureUtilisateurEtat(0);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @GetMapping("/v-annonces-vendu")
    public List<V_Annonce> getFilteredVAnnoncesVendu() {
        try {
            return v_AnnonceRepository.findV_AnnoncesByStatus(10);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @GetMapping("/v-annonces-non-vendu")
    public List<V_Annonce> getFilteredVAnnoncesNonVendu() {
        try {
            return v_AnnonceRepository.findV_AnnoncesByStatus(0);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}