package com.okazcar.okazcar.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.auth.FirebaseAuthException;
import com.okazcar.okazcar.exception.ForgetException;
import com.okazcar.okazcar.models.Users;
import com.okazcar.okazcar.models.dto.UserInsertDto;
import com.okazcar.okazcar.models.dto.UserLoginDto;
import com.okazcar.okazcar.services.UtilisateurService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.okazcar.okazcar.handlers.ResponseHandler.sendResponseData;
import static com.okazcar.okazcar.handlers.ResponseHandler.showError;


@RestController
public class UtilisateurController {
    final UtilisateurService utilisateurService;
    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/utilisateur/signin")
    public String signIn(@ModelAttribute UserInsertDto userDto) throws IOException {
        try {
            Users users = utilisateurService.insert(userDto);
            return prepareToBeSend(users);
        } catch (IOException | FirebaseAuthException | ForgetException e) {
            return showError(e, HttpStatus.FORBIDDEN);
        }
    }

    private String prepareToBeSend(Users users) throws FirebaseAuthException, IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("Token", utilisateurService.generateToken(users.getUtilisateur().getUtilisateurId()));
        map.put("Email", users.getUtilisateur().getEmail());
        map.put("Username", users.getUtilisateur().getUsername());
        if (users.getUserMongoDb() != null) {
            map.put("Image", users.getUserMongoDb().getImage());
        }
        return sendResponseData(map, HttpStatus.ACCEPTED);
    }
    @PostMapping("/utilisateur/login")
    public String logIn(@ModelAttribute UserLoginDto userDto, HttpServletResponse response) throws IOException {
        try {
            Users users = utilisateurService.logIn(userDto);
            return prepareToBeSend(users);
        } catch (IOException | FirebaseAuthException | ForgetException e) {
            return showError(e, HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/utilisateur/users")
    public String getAll() throws IOException {
        try {
            return sendResponseData(utilisateurService.getAll(), HttpStatus.ACCEPTED);
        } catch (JsonProcessingException e) {
            return showError(e, HttpStatus.FORBIDDEN);
        }
    }
}
