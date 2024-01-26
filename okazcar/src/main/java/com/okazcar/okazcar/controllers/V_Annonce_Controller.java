package com.okazcar.okazcar.controllers;

import com.okazcar.okazcar.models.V_Annonce;
import com.okazcar.okazcar.repositories.V_Annonce_Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            @RequestParam(required = false, defaultValue = "") String marque,
            @RequestParam(required = false, defaultValue = "") String type,
            @RequestParam(required = false, defaultValue = "") String dateCreation,
            @RequestParam(required = false, defaultValue = "") String localisation,
            @RequestParam(required = false, defaultValue = "") String prix
    ) throws ParseException {
        try {
            // parse manta
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date parsedDate = dateFormat.parse(dateCreation);
            Timestamp timestamp = new Timestamp(parsedDate.getTime());

            return v_AnnonceRepository.findV_AnnoncesByCategorieAndMarqueAndTypeAndDateCreationAndLocalisationAndPrix
                    (categorie, marque, type, timestamp, localisation, Double.parseDouble(prix));
        } catch (ParseException e) {
           throw e;
        }
    }

    @GetMapping("/v-annonces-valide")
    public List<V_Annonce> getFilteredVAnnoncesValide() {
        try {
            return v_AnnonceRepository.findV_AnnoncesByVoitureUtilisateurEtat(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

}
