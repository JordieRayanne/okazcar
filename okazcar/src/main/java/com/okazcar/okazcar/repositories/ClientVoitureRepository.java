package com.okazcar.okazcar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.okazcar.okazcar.models.ClientVoiture;


@Repository
public interface ClientVoitureRepository extends JpaRepository<ClientVoiture,Integer> {
}
