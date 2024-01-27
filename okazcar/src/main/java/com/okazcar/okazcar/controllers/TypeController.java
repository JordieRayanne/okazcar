package com.okazcar.okazcar.controllers;

import com.okazcar.okazcar.models.Type;
import com.okazcar.okazcar.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TypeController {
    private final TypeService typeService;

    @Autowired
    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping("/types")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<Type>> getAll() {
        List<Type> types = typeService.findAll();
        return new ResponseEntity<>(types, HttpStatus.OK);
    }

    @GetMapping("/types/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Type> getById(@PathVariable int id) {
        Type type = typeService.findOne(id);
        return new ResponseEntity<>(type, HttpStatus.OK);
    }

    @PostMapping("/type")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Type> create(@ModelAttribute Type type) {
        Type createdType = typeService.insert(type);
        return new ResponseEntity<>(createdType, HttpStatus.CREATED);
    }

    @PutMapping("/types/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> update(@PathVariable("id") int id, @ModelAttribute Type type) {
        try {
            type.setId(id);
            Type updatedType = typeService.update(type);
            return new ResponseEntity<>(updatedType, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/types/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable int id) {
        try {
            typeService.delete(id);
            return new ResponseEntity<>("Type id="+id+" deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
