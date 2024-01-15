package com.okazcar.okazcar.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.okazcar.okazcar.model.V_Annonce;
import com.okazcar.okazcar.repository.V_Annonce_Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

}
