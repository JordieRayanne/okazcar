package com.okazcar.okazcar.controllers;

import java.util.List;

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

    @Autowired
    public VoitureController(VoitureService voitureService){
        this.voitureService=voitureService;
    }

    @GetMapping("/voitures")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<Voiture>> getAll(){
        List<Voiture> voitures=voitureService.getAllVoitures();
        return new ResponseEntity<>(voitures,HttpStatus.OK);
    }

    @GetMapping("/voitures/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Voiture> getById(@PathVariable int id){
        Voiture voiture=voitureService.getVoitureById(id);
        return new ResponseEntity<>(voiture,HttpStatus.OK);
    }

    @PostMapping("/voitures")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Voiture> create(@ModelAttribute Voiture voiture){
        Voiture createdVoiture = voitureService.createVoiture(voiture);
        return new ResponseEntity<>(createdVoiture, HttpStatus.CREATED);
    }

    @PutMapping("/voitures/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Voiture> update(@PathVariable int id,@ModelAttribute Voiture voiture){
        Voiture voitureModifiee = voitureService.updateVoiture(id,voiture);
        return new ResponseEntity<>(voitureModifiee,HttpStatus.OK);
    }

    @DeleteMapping("/voitures/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> delete(@PathVariable int id){
        voitureService.deleteVoiture(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
