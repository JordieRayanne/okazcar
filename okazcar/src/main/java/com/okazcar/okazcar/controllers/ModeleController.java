package com.okazcar.okazcar.controllers;

import com.okazcar.okazcar.models.Modele;
import com.okazcar.okazcar.repositories.ModeleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/modeles")
public class ModeleController {
    private final ModeleRepository modeleRepository;

    @Autowired
    public ModeleController(ModeleRepository modeleRepository) {
        this.modeleRepository = modeleRepository;
    }

    @GetMapping
    public List<Modele> getAllModeles() {
        return modeleRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Modele> getModeleById(@PathVariable("id") Integer id) {
        Optional<Modele> modeleOptional = modeleRepository.findById(id);
        return modeleOptional.map(modele -> new ResponseEntity<>(modele, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Modele> createModele(@RequestBody Modele modele) {
        Modele createdModele = modeleRepository.save(modele);
        return new ResponseEntity<>(createdModele, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Modele> updateModele(@PathVariable("id") Integer id, @RequestBody Modele modele) {
        Optional<Modele> existingModeleOptional = modeleRepository.findById(id);
        return existingModeleOptional.map(existingModele -> {
            modele.setId(id);
            Modele updatedModele = modeleRepository.save(modele);
            return new ResponseEntity<>(updatedModele, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteModele(@PathVariable("id") Integer id) {
        try {
            modeleRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
