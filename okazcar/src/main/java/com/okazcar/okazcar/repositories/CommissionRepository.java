package com.okazcar.okazcar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.okazcar.okazcar.models.Commission;

@Repository
public interface CommissionRepository extends JpaRepository<Commission, Integer> {

}
