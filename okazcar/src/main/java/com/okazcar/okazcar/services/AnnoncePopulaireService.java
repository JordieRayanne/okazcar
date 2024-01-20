package com.okazcar.okazcar.services;

import com.okazcar.okazcar.models.Annonce;
import com.okazcar.okazcar.models.file.AnnonceFile;
import com.okazcar.okazcar.repositories.AnnonceRepository;
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

    @Autowired
    public AnnoncePopulaireService (AnnonceRepository annonceRepository, AnnonceFileService annonceFileService) {
        this.annonceRepository = annonceRepository;
        this.annonceFileService = annonceFileService;
    }

    public List<Annonce> getAnnoncesPopulaires() {
        Page<Annonce> annoncesPages = annonceRepository.findAll(PageRequest.of(0, 6));
        List<Annonce> annonces = annoncesPages.getContent();
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
}
