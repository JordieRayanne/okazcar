package com.okazcar.okazcar.controllers;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.okazcar.okazcar.models.Annonce;
import com.okazcar.okazcar.repositories.AnnonceRepository;

@RestController
public class AnnonceController {
    final AnnonceRepository AnnonceRepository;

    @Autowired
    public AnnonceController(AnnonceRepository AnnonceRepository) {
        this.AnnonceRepository = AnnonceRepository;
    }

    @GetMapping("/annonces")
    // @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<Annonce> getAll(){
        return AnnonceRepository.findAll();
    }

    @GetMapping("/annonce/{id}")
    // @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Annonce> getById(@PathVariable("id") Integer id) {
        Optional<Annonce> AnnonceOptional = AnnonceRepository.findById(id);
        return AnnonceOptional.map(Annonce -> new ResponseEntity<>(Annonce, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/annonce")
//    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Annonce> create(@ModelAttribute Annonce Annonce) {
        Annonce createdAnnonce = AnnonceRepository.save(Annonce);
        return new ResponseEntity<>(createdAnnonce, HttpStatus.CREATED);
    }

    @PutMapping("/annonce/{id}")
     @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Annonce> update(@PathVariable("id") Integer id, @ModelAttribute Annonce Annonce) {
        Optional<Annonce> existingAnnonceOptional = AnnonceRepository.findById(id);
        return existingAnnonceOptional.map(existingModele -> {
            Annonce.setId(id);
            Annonce updatedAnnonce = AnnonceRepository.save(Annonce);
            return new ResponseEntity<>(updatedAnnonce, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/annonce/{id}")
    //   @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Integer id) {
        try {
            AnnonceRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}