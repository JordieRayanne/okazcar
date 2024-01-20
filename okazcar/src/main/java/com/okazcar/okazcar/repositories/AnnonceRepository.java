package com.okazcar.okazcar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.okazcar.okazcar.models.Annonce;

import java.util.List;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Integer> {

    List<Annonce> findAnnoncesByStatus(int status);
}
