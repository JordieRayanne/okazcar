package com.okazcar.okazcar.controllers;

import java.util.List;

import com.okazcar.okazcar.models.VoitureVoitureImage;
import com.okazcar.okazcar.models.mongodb.VoitureImage;
import com.okazcar.okazcar.repositories.mongodb.VoitureImageRepository;
import com.okazcar.okazcar.services.VoitureImageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.okazcar.okazcar.models.Voiture;
import com.okazcar.okazcar.services.VoitureService;

@RestController
public class VoitureController {
    private final VoitureService voitureService;
    private final VoitureImageService voitureImageService;

    @Autowired
    public VoitureController(VoitureService voitureService, VoitureImageService voitureImageService){
        this.voitureService=voitureService;
        this.voitureImageService = voitureImageService;
    }

    @GetMapping("/voitures")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<VoitureVoitureImage>> getAll(){
        List<VoitureVoitureImage> voitures=voitureImageService.getAll();
        return new ResponseEntity<>(voitures,HttpStatus.OK);
    }

    @GetMapping("/voitures/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getById(@PathVariable int id){
        try {
            VoitureVoitureImage voiture=voitureImageService.getVoitureImageById(id);
            return new ResponseEntity<>(voiture,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/voiture")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> create(HttpServletRequest request){
        try {
            return new ResponseEntity<>(voitureImageService.insert(request), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/voitures/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> update(@PathVariable int id, HttpServletRequest request){
        try {
            return new ResponseEntity<>(voitureImageService.update(request, id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/voitures/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> delete(@PathVariable int id){
        try {
            voitureService.deleteVoiture(id);
            return new ResponseEntity<>("Voiture id="+ id +" deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
