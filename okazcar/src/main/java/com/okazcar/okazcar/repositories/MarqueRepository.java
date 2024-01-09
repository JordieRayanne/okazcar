package com.okazcar.okazcar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.okazcar.okazcar.models.Marque;

public interface MarqueRepository extends JpaRepository<Marque,Integer> {
    
}
