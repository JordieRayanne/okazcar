package com.okazcar.okazcar.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.okazcar.okazcar.models.Marque;
import com.okazcar.okazcar.services.MarqueService;

@RestController
public class MarqueController {

    private final MarqueService marqueService;

    @Autowired
    public MarqueController(MarqueService marqueService) {
        this.marqueService = marqueService;
    }

    @GetMapping("/marques")
    public ResponseEntity<List<Marque>> getAll() {
        List<Marque> marques = marqueService.getAllMarques();
        return new ResponseEntity<>(marques, HttpStatus.OK);
    }

    @GetMapping("/marques/{id}")
    public ResponseEntity<Marque> getById(@PathVariable int id) {
        Marque marque = marqueService.getMarqueById(id);
        return new ResponseEntity<>(marque, HttpStatus.OK);
    }

    @PostMapping("/marques")
    public ResponseEntity<Marque> create(@ModelAttribute Marque marque) {
        Marque createdMarque = marqueService.createMarque(marque);
        return new ResponseEntity<>(createdMarque, HttpStatus.CREATED);
    }

    @PutMapping("/marques/{id}")
    public ResponseEntity<Marque> update(@PathVariable int id,@ModelAttribute Marque marque) {
        Marque updatedMarque = marqueService.updateMarque(id, marque);
        return new ResponseEntity<>(updatedMarque, HttpStatus.OK);
    }

    @DeleteMapping("/marques/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        marqueService.deleteMarque(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}