package com.okazcar.okazcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.okazcar.okazcar.model.Marque;

public interface MarqueRepository extends JpaRepository<Marque,Integer> {
    
}
