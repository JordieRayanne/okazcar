package com.okazcar.okazcar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.okazcar.okazcar.models.Voiture;
import com.okazcar.okazcar.repositories.VoitureRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class VoitureService {
    private final VoitureRepository voitureRepository;

    @Autowired
    public VoitureService(VoitureRepository voitureRepository){
        this.voitureRepository=voitureRepository;
    }

    public List<Voiture> getAllVoitures(){
        return voitureRepository.findAll();
    }

    public Voiture getVoitureById(int id){
        return voitureRepository.findById(id).orElse(null);
    }

    public Voiture createVoiture(Voiture voiture){
        return voitureRepository.save(voiture);
    }

    public Voiture updateVoiture(int id, Voiture newVoiture){
        return voitureRepository.findById(id)
        .map(voiture ->{
            voiture.setNom(newVoiture.getNom());
            voiture.setCategorie(newVoiture.getCategorie());
            voiture.setMarque(newVoiture.getMarque());
            voiture.setType(newVoiture.getType());
            voiture.setModele(newVoiture.getModele());
            voiture.setCouleur(newVoiture.getCouleur());
            voiture.setLocalisation(newVoiture.getLocalisation());
            voiture.setDateDemande(newVoiture.getDateDemande());
            voiture.setDescription(newVoiture.getDescription());
            try {
                voiture.setPrix(newVoiture.getPrix());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            voiture.setDevise(newVoiture.getDevise());
            return voitureRepository.save(voiture);
        })
        .orElseThrow(()->new EntityNotFoundException("Voiture not found with id:"+id));
    }

    public void deleteVoiture(int id){
        voitureRepository.deleteById(id);
    }
}
