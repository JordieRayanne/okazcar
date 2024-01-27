package com.okazcar.okazcar.controllers;

import java.util.List;

import com.okazcar.okazcar.models.Annonce;
import com.okazcar.okazcar.models.Commission;
import com.okazcar.okazcar.repositories.AnnonceRepository;
import com.okazcar.okazcar.repositories.CommissionRepository;
import com.okazcar.okazcar.repositories.VoitureUtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.okazcar.okazcar.models.VoitureUtilisateur;
import com.okazcar.okazcar.services.VoitureUtilisateurService;

@RestController
public class VoitureUtilisateurController {
    private final VoitureUtilisateurService voitureUtilisateurService;
    private final CommissionRepository commissionRepository;
    private final AnnonceRepository annonceRepository;
    private final VoitureUtilisateurRepository voitureUtilisateurRepository;

    @Autowired
    public VoitureUtilisateurController(VoitureUtilisateurService voitureUtilisateurService,
                                        CommissionRepository commissionRepository,
                                        AnnonceRepository annonceRepository,
                                        VoitureUtilisateurRepository voitureUtilisateurRepository) {
        this.voitureUtilisateurService = voitureUtilisateurService;
        this.commissionRepository = commissionRepository;
        this.annonceRepository = annonceRepository;
        this.voitureUtilisateurRepository = voitureUtilisateurRepository;
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
    public ResponseEntity<?> create(@ModelAttribute VoitureUtilisateur voitureUtilisateur){
        try {
            VoitureUtilisateur createdVoitureUtilisateur = voitureUtilisateurService.createVoitureUtilisateur(voitureUtilisateur);
            return new ResponseEntity<>(createdVoitureUtilisateur,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/voitureUtilisateurs/{id}/to-10")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Transactional
    public ResponseEntity<?> updateEtatTo10(@PathVariable int id){
        try {
            VoitureUtilisateur updateEtat=voitureUtilisateurService.voitureUtilisateurEtatTo10(id);
            Annonce annonce = new Annonce();
            Commission commission = commissionRepository.getLatestCommissionByDateHeureCommission();
            double prix = (updateEtat.getPrix() * commission.getCommission())/100;
            annonce.setPrixCommission(prix);
            annonce.setPrixVoiture(updateEtat.getPrix());
            annonce.setVoitureUtilisateur(updateEtat);
            annonceRepository.save(annonce);
            return new ResponseEntity<>(updateEtat,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/voitureUtilisateurs_validated")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getVoitureUtilisateurValide() {
        try {
            List<VoitureUtilisateur> voitureUtilisateurs = voitureUtilisateurRepository.findVoitureUtilisateursByEtat(10);
            return new ResponseEntity<>(voitureUtilisateurs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/voitureUtilisateurs_not_validated")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getVoitureUtilisateurNonValide() {
        try {
            List<VoitureUtilisateur> voitureUtilisateurs = voitureUtilisateurRepository.findVoitureUtilisateursByEtat(0);
            return new ResponseEntity<>(voitureUtilisateurs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
