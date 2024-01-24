package com.okazcar.okazcar.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.okazcar.okazcar.models.Annonce;


@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Integer> {

    List<Annonce> findAnnoncesByStatus(int status);
}
