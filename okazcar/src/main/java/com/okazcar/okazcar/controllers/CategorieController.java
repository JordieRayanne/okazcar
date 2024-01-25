package com.okazcar.okazcar.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.okazcar.okazcar.models.Categorie;
import com.okazcar.okazcar.repositories.CategorieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Optional;

import java.util.List;

@RestController
public class CategorieController {
    final CategorieRepository categorieRepository;

    @Autowired
    public CategorieController(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    @GetMapping("/categories")
    public List<Categorie> getAll(){
        return categorieRepository.findAll();
    }

    @GetMapping("/categorie/{id}")
    public ResponseEntity<Categorie> getById(@PathVariable("id") Integer id) {
        Optional<Categorie> categorieOptional = categorieRepository.findById(id);
        return categorieOptional.map(categorie -> new ResponseEntity<>(categorie, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/categorie")
    public ResponseEntity<Categorie> create(@ModelAttribute Categorie categorie) {
        Categorie createdCategorie = categorieRepository.save(categorie);
        return new ResponseEntity<>(createdCategorie, HttpStatus.CREATED);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<Categorie> update(@PathVariable("id") Integer id, @ModelAttribute Categorie categorie) {
        Optional<Categorie> existingCategorieOptional = categorieRepository.findById(id);
        return existingCategorieOptional.map(existingModele -> {
            categorie.setId(id);
            Categorie updatedCategorie = categorieRepository.save(categorie);
            return new ResponseEntity<>(updatedCategorie, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        try {
            categorieRepository.deleteById(id);
            return new ResponseEntity<>("Catégorie id="+id+" deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
