package com.okazcar.okazcar.controllers;

import com.okazcar.okazcar.models.Devise;
import com.okazcar.okazcar.services.DeviseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeviseController {

    private final DeviseService deviseService;

    @Autowired
    public DeviseController(DeviseService deviseService) {
        this.deviseService = deviseService;
    }

    @GetMapping("/devises")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<Devise>> getAll() {
        List<Devise> devises = deviseService.findAll();
        return new ResponseEntity<>(devises, HttpStatus.OK);
    }

    @GetMapping("/devises/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Devise> getById(@PathVariable int id) {
        Devise devise = deviseService.findOne(id);
        return new ResponseEntity<>(devise, HttpStatus.OK);
    }

    @PostMapping("/devises")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Devise> create(@ModelAttribute Devise devise) {
        Devise createdDevise = deviseService.insert(devise);
        return new ResponseEntity<>(createdDevise, HttpStatus.CREATED);
    }

    @PutMapping("/devises/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Devise> update(@PathVariable("id") int id, @ModelAttribute Devise devise) {
        devise.setId(id);
        Devise updatedDevise = deviseService.update(devise);
        return new ResponseEntity<>(updatedDevise, HttpStatus.OK);
    }

    @DeleteMapping("/devises/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        deviseService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
