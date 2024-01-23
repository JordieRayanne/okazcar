package com.okazcar.okazcar.controllers;

import com.okazcar.okazcar.models.Utilisateur;
import com.okazcar.okazcar.models.dto.MessageDto;
import com.okazcar.okazcar.models.mongodb.Conversation;
import com.okazcar.okazcar.services.ConversationService;
import com.okazcar.okazcar.services.UtilisateurService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class ConversationController {

    private final ConversationService conversationService;
    private final UtilisateurService utilisateurService;
    public ConversationController(ConversationService conversationService, UtilisateurService utilisateurService) {
        this.conversationService = conversationService;
        this.utilisateurService = utilisateurService;
    }
    @PostMapping("/conversation")
    //@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Conversation> addMessage(@ModelAttribute MessageDto messageDto) {
        try {
            return new ResponseEntity<>(conversationService.insert(messageDto), HttpStatus.OK);
        }catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/conversations/{personId}")
    //@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Conversation> findByPerson(@RequestParam("personId") String personId, HttpServletRequest request) {
        Utilisateur utilisateur = utilisateurService.extractUtilisateurFromHttpServletRequest(request);
        return new ResponseEntity<>(conversationService.getConversations(utilisateur.getUtilisateurId(), personId).get(0), HttpStatus.OK);
    }
}
