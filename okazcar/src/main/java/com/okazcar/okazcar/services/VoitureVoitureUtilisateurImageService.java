package com.okazcar.okazcar.services;

import com.okazcar.okazcar.models.VoitureUtilisateur;
import com.okazcar.okazcar.models.VoitureVoitureUtilisateurImage;
import com.okazcar.okazcar.repositories.mongodb.VoitureImageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VoitureVoitureUtilisateurImageService {

    private final VoitureImageRepository voitureImageRepository;

    public VoitureVoitureUtilisateurImageService(VoitureImageRepository voitureImageRepository) {
        this.voitureImageRepository = voitureImageRepository;
    }

    public List<VoitureVoitureUtilisateurImage> getVoitureUtilisateursWithImage(List<VoitureUtilisateur> voitureUtilisateurs) {
        List<VoitureVoitureUtilisateurImage> voitureVoitureUtilisateurImages = new ArrayList<>();
        for (VoitureUtilisateur voitureUtilisateur: voitureUtilisateurs) {
            voitureVoitureUtilisateurImages.add(new VoitureVoitureUtilisateurImage(voitureUtilisateur, voitureImageRepository.findVoitureImageByVoitureId(voitureUtilisateur.getVoiture().getId())));
        }
        return voitureVoitureUtilisateurImages;
    }
}
