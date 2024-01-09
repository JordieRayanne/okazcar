package com.okazcar.okazcar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.okazcar.okazcar.model.Marque;
import com.okazcar.okazcar.repository.MarqueRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MarqueService {

    private final MarqueRepository marqueRepository;

    @Autowired
    public MarqueService(MarqueRepository marqueRepository) {
        this.marqueRepository = marqueRepository;
    }

    public List<Marque> getAllMarques() {
        return marqueRepository.findAll();
    }

    public Marque getMarqueById(int id) {
        return marqueRepository.findById(id).orElse(null);
    }

    public Marque createMarque(Marque marque) {
        return marqueRepository.save(marque);
    }     

    public Marque updateMarque(int id, Marque newMarque) {
        return marqueRepository.findById(id)
                .map(marque -> {
                    marque.setNom(newMarque.getNom());
                    return marqueRepository.save(marque);
                })
                .orElseThrow(() -> new EntityNotFoundException("Marque not found with id: " + id));
    }

    public void deleteMarque(int id) {
        marqueRepository.deleteById(id);
    }
}
