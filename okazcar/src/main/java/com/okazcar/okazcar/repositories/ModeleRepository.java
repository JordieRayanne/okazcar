package com.okazcar.okazcar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.okazcar.okazcar.models.Modele;

@Repository
public interface ModeleRepository extends JpaRepository<Modele,Integer>{
    //automatic crud function herited automatically
}
