package com.okazcar.okazcar.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.okazcar.okazcar.models.VoitureUtilisateur;
import com.okazcar.okazcar.services.VoitureUtilisateurService;

@RestController
public class VoitureUtilisateurController {
    private final VoitureUtilisateurService voitureUtilisateurService;

    @Autowired
    public VoitureUtilisateurController(VoitureUtilisateurService voitureUtilisateurService) {
        this.voitureUtilisateurService = voitureUtilisateurService;
    }

    @GetMapping("/voitureUtilisateurs")
    //   @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<VoitureUtilisateur>> getAll() {
        List<VoitureUtilisateur> voitureUtilisateurs= voitureUtilisateurService.getAllVoitureUtilisateurs();
        return new ResponseEntity<>(voitureUtilisateurs, HttpStatus.OK);
    }

    @PostMapping("/voitureUtilisateurs")
    //  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<VoitureUtilisateur> create(@ModelAttribute VoitureUtilisateur voitureUtilisateur){
        VoitureUtilisateur createdVoitureUtilisateur = voitureUtilisateurService.createVoitureUtilisateur(voitureUtilisateur);
        return new ResponseEntity<>(createdVoitureUtilisateur,HttpStatus.CREATED);
    }
}
