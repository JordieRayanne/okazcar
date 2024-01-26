package com.okazcar.okazcar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.okazcar.okazcar.models.Commission;

@Repository
public interface CommissionRepository extends JpaRepository<Commission, Integer> {

    @Query("SELECT c FROM commission c ORDER BY c.dateHeureCommission DESC LIMIT 1")
    Commission getLatestCommissionByDateHeureCommission();
}
