package com.okazcar.okazcar.repositories;

import com.okazcar.okazcar.models.HistoricLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistoricLoginRepository extends JpaRepository<HistoricLogin, String> {
    Optional<HistoricLogin> findHistoricLoginByCodeChiffreAndUtilisateurId(String codeChiffre, String utilisateurId);
}
