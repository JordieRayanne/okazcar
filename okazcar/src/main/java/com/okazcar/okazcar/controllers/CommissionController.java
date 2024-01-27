package com.okazcar.okazcar.controllers;

import com.okazcar.okazcar.models.Commission;
import com.okazcar.okazcar.services.CommissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin(origins = "")
public class CommissionController {
    private final CommissionService commissionService;

    @Autowired
    public CommissionController(CommissionService commissionService) {
        this.commissionService = commissionService;
    }

    @PostMapping("/commission")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> insert(@ModelAttribute Commission commission) {
        try {
            return new ResponseEntity<>(commissionService.insert(commission), HttpStatus.OK) ;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK) ;
        }
    }

    @GetMapping("/commissions")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<Commission>> getAll() {
        return new ResponseEntity<>(commissionService.findAll(), HttpStatus.OK);
    }
}
