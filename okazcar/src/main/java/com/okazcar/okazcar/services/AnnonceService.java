package com.okazcar.okazcar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.okazcar.okazcar.models.Annonce;
import com.okazcar.okazcar.repositories.AnnonceRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class AnnonceService {
    private final AnnonceRepository annonceRepository;

    @Autowired
    public AnnonceService(AnnonceRepository marqueRepository) {
        this.annonceRepository = marqueRepository;
    }

    public Annonce updateStatus(int id, Annonce newannonce){
        return annonceRepository.findById(id)
                .map(annonce -> {
                    annonce.setStatus(newannonce.getStatus());
                    return annonceRepository.save(annonce);
                })
                .orElseThrow(() -> new EntityNotFoundException("Marque not found with id: " + id));
    }
}
