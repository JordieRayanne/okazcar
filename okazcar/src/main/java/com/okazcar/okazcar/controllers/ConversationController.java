package com.okazcar.okazcar.controllers;

import com.okazcar.okazcar.models.Contact;
import com.okazcar.okazcar.models.HistoriqueMessage;
import com.okazcar.okazcar.models.Utilisateur;
import com.okazcar.okazcar.models.dto.MessageDto;
import com.okazcar.okazcar.models.mongodb.Conversation;
import com.okazcar.okazcar.services.ConversationService;
import com.okazcar.okazcar.services.HistoriqueMessageService;
import com.okazcar.okazcar.services.UtilisateurService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class ConversationController {

    private final ConversationService conversationService;
    private final UtilisateurService utilisateurService;
    private final HistoriqueMessageService historiqueMessageService;

    @Autowired
    public ConversationController(ConversationService conversationService, UtilisateurService utilisateurService, HistoriqueMessageService historiqueMessageService) {
        this.conversationService = conversationService;
        this.utilisateurService = utilisateurService;
        this.historiqueMessageService = historiqueMessageService;
    }
    @PostMapping("/conversation")
    //@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> addMessage(@ModelAttribute MessageDto messageDto) {

        try {
            return new ResponseEntity<>(conversationService.insert(messageDto), HttpStatus.OK);
        }catch (IOException e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/conversations/{personId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Conversation> findByPerson(@RequestParam("personId") String personId, HttpServletRequest request) {
        Utilisateur utilisateur = utilisateurService.extractUtilisateurFromHttpServletRequest(request);
        return new ResponseEntity<>(conversationService.getConversations(utilisateur.getUtilisateurId(), personId).get(0), HttpStatus.OK);
    }

    @GetMapping("/contacts")
    //@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getMyContact(HttpServletRequest request) {
        try {
            return new ResponseEntity<>(historiqueMessageService.getContact(request), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
