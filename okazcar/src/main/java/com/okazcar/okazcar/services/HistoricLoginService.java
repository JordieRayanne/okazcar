package com.okazcar.okazcar.services;

import com.okazcar.okazcar.models.HistoricLogin;
import com.okazcar.okazcar.models.Utilisateur;
import com.okazcar.okazcar.repositories.HistoricLoginRepository;
import com.okazcar.okazcar.repositories.UtilisateurRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class HistoricLoginService {
    private final HistoricLoginRepository historicLoginRepository;
    private final UtilisateurService utilisateurService;
    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public HistoricLoginService(HistoricLoginRepository historicLoginRepository, UtilisateurService utilisateurService, UtilisateurRepository utilisateurRepository) {
        this.historicLoginRepository = historicLoginRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurService = utilisateurService;
    }

    public HistoricLogin insert(HttpServletRequest request) {
        Utilisateur utilisateur = extractUtilisateurFromHttpServletRequest(request);
        HistoricLogin historicLogin = new HistoricLogin();
        historicLogin.setToken(utilisateurService.generateToken(utilisateur.getEmail()));
        historicLogin.setCodeChiffre(generateCodeChiffre());
        historicLogin.setUtilisateurId(utilisateur.getUtilisateurId());
        return historicLoginRepository.save(historicLogin);
    }

    private Utilisateur extractUtilisateurFromHttpServletRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String token = authorizationHeader.replace("Bearer ", "");
        String email = utilisateurService.extractUsername(token);
        return utilisateurRepository.findUtilisateurByEmail(email);
    }

    public HistoricLogin login(HttpServletRequest request) throws SQLException {
        String codeChiffre = request.getParameter("codeChiffre");
        String email = request.getParameter("email");
        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByEmail(email);
        if (utilisateur == null) {
            throw new SQLException("The code chiffre: <b>" + codeChiffre + "</b> is not found for the email: <b>" + email + "</b>");
        }
        Optional<HistoricLogin> historicLogin = historicLoginRepository.findHistoricLoginByCodeChiffreAndUtilisateurId(codeChiffre, utilisateur.getUtilisateurId());
        if (historicLogin.isPresent()) {
            return historicLogin.get();
        }
        throw new SQLException("The code chiffre: <b>" + codeChiffre + "</b> is not found for the user: <b>" + utilisateur.getUsername() + "</b>");
    }

    private String generateCodeChiffre() {
        String toReturn = LocalDateTime.now().toString();
        String[] parts = toReturn.split("\\.");
        return parts[0].replace("T", "").replace(":", "").replace("-", "");
    }
}
