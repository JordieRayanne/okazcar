package com.okazcar.okazcar.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<Marque>> getAll() {
        List<Marque> marques = marqueService.getAllMarques();
        return new ResponseEntity<>(marques, HttpStatus.OK);
    }

    @GetMapping("/marques/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Marque> getById(@PathVariable int id) {
        Marque marque = marqueService.getMarqueById(id);
        return new ResponseEntity<>(marque, HttpStatus.OK);
    }

    @PostMapping("/marque")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Marque> create(@ModelAttribute Marque marque) {
        Marque createdMarque = marqueService.createMarque(marque);
        return new ResponseEntity<>(createdMarque, HttpStatus.CREATED);
    }

    @PutMapping("/marques/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Marque> update(@PathVariable int id,@ModelAttribute Marque marque) {
        Marque updatedMarque = marqueService.updateMarque(id, marque);
        return new ResponseEntity<>(updatedMarque, HttpStatus.OK);
    }

    @DeleteMapping("/marques/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable int id) {
        try {
            marqueService.deleteMarque(id);
            return new ResponseEntity<>("Marque id="+id+" deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
