package com.okazcar.okazcar.controllers;

import com.okazcar.okazcar.repositories.VoitureUtilisateurRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.okazcar.okazcar.models.Annonce;
import com.okazcar.okazcar.repositories.AnnonceRepository;

@RestController
public class AnnonceController {
    final AnnonceRepository annonceRepository;
    private final VoitureUtilisateurRepository voitureUtilisateurRepository;

    @Autowired
    public AnnonceController(AnnonceRepository annonceRepository,
                             VoitureUtilisateurRepository voitureUtilisateurRepository) {
        this.annonceRepository = annonceRepository;
        this.voitureUtilisateurRepository = voitureUtilisateurRepository;
    }

    @GetMapping("/annonces")
    public List<Annonce> getAll(){
        List<Annonce> annonces = annonceRepository.findAll();
        for (int i = 0; i < annonces.size(); i++) {
            annonces.get(i).getVoitureUtilisateur().getUtilisateur().setPassword(null);
            annonces.get(i).getVoitureUtilisateur().getUtilisateur().setRoles(null);
        }
        return annonces;
    }

    @GetMapping("/annonces/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Annonce> getById(@PathVariable("id") Integer id) {
        Optional<Annonce> AnnonceOptional = annonceRepository.findById(id);
        return AnnonceOptional.map(Annonce -> new ResponseEntity<>(Annonce, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/annonces/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Annonce> update(@PathVariable("id") Integer id, @ModelAttribute Annonce annonce) {
        Optional<Annonce> existingAnnonceOptional = annonceRepository.findById(id);
        return existingAnnonceOptional.map(existingModele -> {
            annonce.setId(id);
            Annonce updatedAnnonce = annonceRepository.save(annonce);
            updatedAnnonce.getVoitureUtilisateur().getUtilisateur().setPassword(null);
            updatedAnnonce.getVoitureUtilisateur().getUtilisateur().setRoles(null);
            return new ResponseEntity<>(updatedAnnonce, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/annonces_vendu/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Annonce> updateStatusVendu(@PathVariable("id") Integer id) {
        Optional<Annonce> existingAnnonceOptional = annonceRepository.findById(id);
        return existingAnnonceOptional.map(existingModele -> {
            existingModele.setStatus(10);
            existingModele.setDateVente(Timestamp.valueOf(LocalDateTime.now()));
            Annonce updatedAnnonce = annonceRepository.save(existingModele);
            updatedAnnonce.getVoitureUtilisateur().getUtilisateur().setPassword(null);
            updatedAnnonce.getVoitureUtilisateur().getUtilisateur().setRoles(null);
            return new ResponseEntity<>(updatedAnnonce, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/annonces/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Transactional
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        try {
            Annonce annonce = annonceRepository.findAnnonceById(id);
            annonceRepository.deleteById(id);
            voitureUtilisateurRepository.deleteById(annonce.getVoitureUtilisateur().getId());
            return new ResponseEntity<>("Annonce id="+id +" deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}