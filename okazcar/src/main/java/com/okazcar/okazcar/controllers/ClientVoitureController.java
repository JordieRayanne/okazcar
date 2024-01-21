package com.okazcar.okazcar.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.okazcar.okazcar.models.ClientVoiture;
import com.okazcar.okazcar.services.ClientVoitureService;

@RestController
public class ClientVoitureController {
    private final ClientVoitureService clientVoitureService;

    @Autowired
    public ClientVoitureController(ClientVoitureService clientVoitureService){
        this.clientVoitureService = clientVoitureService;
    }
    @GetMapping("/clientVoitures")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<ClientVoiture>> getAll(){
        List<ClientVoiture> clientVoitures=clientVoitureService.getAllClientVoiture();
        return new ResponseEntity<>(clientVoitures,HttpStatus.OK);
    }

    @PostMapping("/clientVoitures")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity <ClientVoiture> create(@ModelAttribute ClientVoiture clientVoiture){
        ClientVoiture createdClientVoiture=clientVoitureService.createClientVoiture(clientVoiture);
        return new ResponseEntity<>(createdClientVoiture,HttpStatus.CREATED);
    }
}
