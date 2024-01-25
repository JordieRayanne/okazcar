package com.okazcar.okazcar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.okazcar.okazcar.models.Voiture;

public interface VoitureRepository extends JpaRepository<Voiture,Integer>{
    Voiture findVoitureById(int voitureId);
}
