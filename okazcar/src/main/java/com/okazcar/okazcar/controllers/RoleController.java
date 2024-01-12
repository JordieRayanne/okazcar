package com.okazcar.okazcar.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.okazcar.okazcar.models.Role;
import com.okazcar.okazcar.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.okazcar.okazcar.handlers.ResponseHandler.sendResponseData;
import static com.okazcar.okazcar.handlers.ResponseHandler.showError;

@RestController
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String findAll() throws IOException {
        try {
            return sendResponseData(roleService.findAll(), HttpStatus.ACCEPTED);
        } catch (JsonProcessingException e) {
            return showError(e, HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/role/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String findOne(@PathVariable("id") int id) throws IOException {
        try {
            return sendResponseData(roleService.findOne(id), HttpStatus.ACCEPTED);
        } catch (JsonProcessingException e) {
            return showError(e, HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/role")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String signIn(@ModelAttribute Role role) throws IOException {
        try {
            return sendResponseData(roleService.insert(role), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return showError(e, HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/role")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String update(@ModelAttribute Role role) throws IOException {
        try {
            return sendResponseData(roleService.update(role), HttpStatus.ACCEPTED);
        } catch (JsonProcessingException e) {
            return showError(e, HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/role/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String delete(@PathVariable("id") int id) throws IOException {
        try {
            return sendResponseData(roleService.delete(id), HttpStatus.ACCEPTED);
        } catch (JsonProcessingException e) {
            return showError(e, HttpStatus.FORBIDDEN);
        }
    }
}
