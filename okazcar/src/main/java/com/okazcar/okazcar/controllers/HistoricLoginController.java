package com.okazcar.okazcar.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.okazcar.okazcar.handlers.ResponseHandler;
import com.okazcar.okazcar.models.HistoricLogin;
import com.okazcar.okazcar.models.Users;
import com.okazcar.okazcar.repositories.mongodb.UserMongoDbRepository;
import com.okazcar.okazcar.services.HistoricLoginService;
import com.okazcar.okazcar.services.UtilisateurService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@RestController
public class HistoricLoginController {
    private final HistoricLoginService historicLoginService;
    private final UserMongoDbRepository userMongoDbRepository;
    private final UtilisateurService utilisateurService;
    @Autowired
    public HistoricLoginController(HistoricLoginService historicLoginService, UserMongoDbRepository userMongoDbRepository, UtilisateurService utilisateurService) {
        this.historicLoginService = historicLoginService;
        this.userMongoDbRepository = userMongoDbRepository;
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/historicLogin")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String insert(HttpServletRequest request) throws JsonProcessingException {
        return ResponseHandler.sendResponseData(historicLoginService.insert(request).getCodeChiffre(), HttpStatus.ACCEPTED);
    }

    @PostMapping("/utilisateur/historicLogin")
    public String login(HttpServletRequest request) throws JsonProcessingException {
        try {
            HistoricLogin historicLogin = historicLoginService.login(request);
            String token = historicLogin.getToken();
            Users users = new Users(historicLogin.getUtilisateur(), userMongoDbRepository.findUserMongoDbByUserId(historicLogin.getUtilisateur().getUtilisateurId()));
            return ResponseHandler.prepareToBeSend(token, users, utilisateurService);
        } catch (SQLException | IOException e) {
            return ResponseHandler.generateErrorResponse(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (ExpiredJwtException e) {
            return ResponseHandler.generateErrorResponse("Votre code à 10 chiffres est expiré. Générer un nouveau", HttpStatus.FORBIDDEN);
        }
    }
}
