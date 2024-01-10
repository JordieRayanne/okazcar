package com.okazcar.okazcar.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.auth.FirebaseAuthException;
import com.okazcar.okazcar.details.UtilisateurDetails;
import com.okazcar.okazcar.exception.ForgetException;
import com.okazcar.okazcar.models.Users;
import com.okazcar.okazcar.models.dto.UserInsertDto;
import com.okazcar.okazcar.models.dto.UserLoginDto;
import com.okazcar.okazcar.services.UtilisateurService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.okazcar.okazcar.handlers.ResponseHandler.*;


@RestController
public class UtilisateurController {
    final UtilisateurService utilisateurService;
    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    private String prepareToBeSend(Users users) throws IOException {
        String token = utilisateurService.generateToken(users.getUtilisateur().getEmail());
        SecurityContextHolder.getContext().setAuthentication(utilisateurService.getAuthenticationToken(token, new UtilisateurDetails(users.getUtilisateur(), users.getUtilisateur().getRoles())));
        Map<String, Object> map = new HashMap<>();
        map.put("Token", token);
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
        } catch (Exception e) {
            return showError(e, HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/utilisateurs")
    public String getAll() throws IOException {
        try {
            return sendResponseData(utilisateurService.getAll(), HttpStatus.ACCEPTED);
        } catch (JsonProcessingException e) {
            return showError(e, HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/utilisateur")
    public String signIn(@ModelAttribute UserInsertDto userDto) throws IOException {
        try {
            Users users = utilisateurService.insert(userDto);
            return prepareToBeSend(users);
        } catch (Exception e) {
            return showError(e, HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/utilisateur")
    public String update(@ModelAttribute UserInsertDto userDto) throws IOException {
        try {
            return sendResponseData(utilisateurService.update(userDto), HttpStatus.ACCEPTED);
        } catch (JsonProcessingException | ForgetException | FirebaseAuthException e) {
            return showError(e, HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/utilisateur/{id}")
    public String delete(@PathVariable("id") String id) throws IOException {
        try {
            return sendResponseData(utilisateurService.delete(id), HttpStatus.ACCEPTED);
        } catch (FirebaseAuthException | JsonProcessingException e) {
            return showError(e, HttpStatus.FORBIDDEN);
        }
    }
}
