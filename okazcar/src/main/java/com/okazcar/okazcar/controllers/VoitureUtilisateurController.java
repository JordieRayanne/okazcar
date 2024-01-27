package com.okazcar.okazcar.controllers;

import java.util.List;

import com.okazcar.okazcar.models.Annonce;
import com.okazcar.okazcar.models.Commission;
import com.okazcar.okazcar.repositories.AnnonceRepository;
import com.okazcar.okazcar.repositories.CommissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.okazcar.okazcar.models.VoitureUtilisateur;
import com.okazcar.okazcar.services.VoitureUtilisateurService;

@RestController
@CrossOrigin(origins = "")
public class VoitureUtilisateurController {
    private final VoitureUtilisateurService voitureUtilisateurService;
    private final CommissionRepository commissionRepository;
    private final AnnonceRepository annonceRepository;

    @Autowired
    public VoitureUtilisateurController(VoitureUtilisateurService voitureUtilisateurService,
                                        CommissionRepository commissionRepository,
                                        AnnonceRepository annonceRepository) {
        this.voitureUtilisateurService = voitureUtilisateurService;
        this.commissionRepository = commissionRepository;
        this.annonceRepository = annonceRepository;
    }

    @GetMapping("/voitureUtilisateurs")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<VoitureUtilisateur>> getAll() {
        List<VoitureUtilisateur> voitureUtilisateurs= voitureUtilisateurService.getAllVoitureUtilisateurs();
        for (VoitureUtilisateur voitureUtilisateur: voitureUtilisateurs) {
            voitureUtilisateur.getUtilisateur().setPassword(null);
            voitureUtilisateur.getUtilisateur().setRoles(null);
        }
        return new ResponseEntity<>(voitureUtilisateurs, HttpStatus.OK);
    }

    @PostMapping("/voitureUtilisateur")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<VoitureUtilisateur> create(@ModelAttribute VoitureUtilisateur voitureUtilisateur){
        VoitureUtilisateur createdVoitureUtilisateur = voitureUtilisateurService.createVoitureUtilisateur(voitureUtilisateur);
        createdVoitureUtilisateur.getUtilisateur().setPassword(null);
        createdVoitureUtilisateur.getUtilisateur().setRoles(null);
        return new ResponseEntity<>(createdVoitureUtilisateur,HttpStatus.CREATED);
    }

    @PutMapping("/voitureUtilisateurs/{id}/to-10")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Transactional
    public ResponseEntity<VoitureUtilisateur> updateEtatTo10(@PathVariable int id){
        VoitureUtilisateur updateEtat=voitureUtilisateurService.voitureUtilisateurEtatTo10(id);
        Annonce annonce = new Annonce();
        Commission commission = commissionRepository.getLatestCommissionByDateHeureCommission();
        double prix = (updateEtat.getPrix() * commission.getCommission())/100;
        annonce.setPrixCommission(prix);
        annonce.setPrixVoiture(updateEtat.getPrix());
        annonce.setVoitureUtilisateur(updateEtat);
        annonceRepository.save(annonce);
        updateEtat.getUtilisateur().setRoles(null);
        updateEtat.getUtilisateur().setPassword(null);
        return new ResponseEntity<>(updateEtat,HttpStatus.OK);
    }
}
