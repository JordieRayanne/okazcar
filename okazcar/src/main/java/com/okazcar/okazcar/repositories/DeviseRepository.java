package com.okazcar.okazcar.repositories;

import com.okazcar.okazcar.models.Devise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviseRepository extends JpaRepository<Devise, Integer> {
}
