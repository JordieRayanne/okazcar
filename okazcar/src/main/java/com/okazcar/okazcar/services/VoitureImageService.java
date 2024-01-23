package com.okazcar.okazcar.services;

import com.okazcar.okazcar.models.Voiture;
import com.okazcar.okazcar.models.VoitureVoitureImage;
import com.okazcar.okazcar.models.mongodb.VoitureImage;
import com.okazcar.okazcar.repositories.mongodb.VoitureImageRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoitureImageService {
    private final VoitureImageRepository voitureImageRepository;
    private final VoitureService voitureService;
    @Autowired
    public VoitureImageService(VoitureImageRepository voitureImageRepository, VoitureService voitureService) {
        this.voitureImageRepository = voitureImageRepository;
        this.voitureService=voitureService;
    }

    public VoitureVoitureImage insert(HttpServletRequest request) throws Exception {
        Voiture voiture = new Voiture(request);
        Voiture createdVoiture = voitureService.createVoiture(voiture);
        VoitureImage voitureImage = new VoitureImage(request, voiture);
        voitureImage = voitureImageRepository.save(voitureImage);
        return new VoitureVoitureImage(voitureImage, createdVoiture);
    }

    public VoitureVoitureImage update(HttpServletRequest request, int id) throws Exception {
        Voiture voiture = new Voiture(request);
        voiture.setId(id);
        Voiture voitureModifiee = voitureService.updateVoiture(id,voiture);
        VoitureImage voitureImage = new VoitureImage(request, voitureModifiee);
        voitureImage = voitureImageRepository.save(voitureImage);
        return new VoitureVoitureImage(voitureImage, voitureModifiee);
    }
}
