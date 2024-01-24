package com.okazcar.okazcar.controllers;

import com.okazcar.okazcar.models.V_Annonce;
import com.okazcar.okazcar.repositories.V_Annonce_Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
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

    // @GetMapping("/v_annonce/{categorie}/{marque}/{type}/{dateCreation}/{localisation}/{prix}")
    // public ResponseEntity<List<V_Annonce>> getFilteredAnnouncements(
    //     @PathVariable(name = "categorie") String categorie,
    //     @PathVariable(name = "marque") String marque,
    //     @PathVariable(name = "type") String type,
    //     @PathVariable(name = "dateCreation") String dateCreation,
    //     @PathVariable(name = "localisation") String localisation,
    //     @PathVariable(name = "prix") String prix
    // ) {
    //     String query = "SELECT * FROM v_annonce WHERE " +
    //             "categorie LIKE '%" + categorie + "%' AND " +
    //             "marque LIKE '%" + marque + "%' AND " +
    //             "type LIKE '%" + type + "%' AND " +
    //             "date_creation::text LIKE '%" + dateCreation + "%' AND " +
    //             "localisation LIKE '%" + localisation + "%' AND " +
    //             "prix::text LIKE '%" + prix + "%';";

    //     List<V_Annonce> result = executeYourQueryMethod(query);

    //     return result.isEmpty()
    //             ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
    //             : new ResponseEntity<>(result, HttpStatus.OK);
    // }


    @GetMapping("/v-annonces")
    public List<V_Annonce> getFilteredVAnnonces(
            @RequestParam(required = false, defaultValue = "") String categorie,
            @RequestParam(required = false, defaultValue = "") String marque,
            @RequestParam(required = false, defaultValue = "") String type,
            @RequestParam(required = false, defaultValue = "") String dateCreation,
            @RequestParam(required = false, defaultValue = "") String localisation,
            @RequestParam(required = false, defaultValue = "") String prix
    ) {        
        try {
            // parse manta 
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date parsedDate = dateFormat.parse(dateCreation);
            Timestamp timestamp = new Timestamp(parsedDate.getTime());
            
            return v_AnnonceRepository.findV_AnnoncesByCategorieAndMarqueAndTypeAndDateCreationAndLocalisationAndPrix
            (categorie, marque, type, timestamp, localisation, Double.parseDouble(prix));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
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
