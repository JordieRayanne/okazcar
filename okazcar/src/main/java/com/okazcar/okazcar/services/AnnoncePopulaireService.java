package com.okazcar.okazcar.services;

import com.okazcar.okazcar.models.Annonce;
import com.okazcar.okazcar.models.ClientVoiture;
import com.okazcar.okazcar.models.file.AnnonceFile;
import com.okazcar.okazcar.repositories.AnnonceRepository;
import com.okazcar.okazcar.repositories.ClientVoitureRepository;
import com.okazcar.okazcar.services.file.AnnonceFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AnnoncePopulaireService {
    final AnnonceRepository annonceRepository;
    final AnnonceFileService annonceFileService;
    final ClientVoitureRepository clientVoitureRepository;

    @Autowired
    public AnnoncePopulaireService (AnnonceRepository annonceRepository, AnnonceFileService annonceFileService, ClientVoitureRepository clientVoitureRepository) {
        this.annonceRepository = annonceRepository;
        this.clientVoitureRepository = clientVoitureRepository;
        this.annonceFileService = annonceFileService;
    }

    public List<Annonce> getAnnoncesPopulaires() {
       List<Annonce> annonces = annonceRepository.findAll();
        List<Annonce> toReturn = new ArrayList<>();
        List<AnnonceFile> annonceFiles = annonceFileService.getAnnonceFiles();
        annonceFiles = annonceFiles.stream().sorted(Comparator.comparing(AnnonceFile::getTotalCount)).toList();
        Optional<Annonce> annonceOptional;
        for (AnnonceFile annonceFile: annonceFiles) {
            annonceOptional = annonces.stream().filter(annonce -> (annonce.getId()==annonceFile.getArticleId())).findFirst();
            annonceOptional.ifPresent(toReturn::add);
        }
        return toReturn;
    }

    public List<Annonce> getAnnoncesFavoris() {
        List<Annonce> annonces = new ArrayList<>();
        List<ClientVoiture> clientVoitures = clientVoitureRepository.findClientVoituresByFavori(1);
        for (ClientVoiture clientVoiture: clientVoitures) {
            if (!checkClientVoiture(clientVoiture.getAnnonce(), annonces)) {
                annonces.add(clientVoiture.getAnnonce());
            }
        }
        return annonces;
    }

    private boolean checkClientVoiture(Annonce newAnnonce, List<Annonce> annonces) {
        for (Annonce annonce: annonces) {
            if (annonce.getId() == newAnnonce.getId())
                return true;
        }
        return false;
    }
}
