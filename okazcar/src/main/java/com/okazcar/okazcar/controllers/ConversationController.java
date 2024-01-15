package com.okazcar.okazcar.controllers;

import com.okazcar.okazcar.models.dto.MessageDto;
import com.okazcar.okazcar.models.mongodb.Conversation;
import com.okazcar.okazcar.services.ConversationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class ConversationController {

    private final ConversationService conversationService;
    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }
    @PostMapping("/conversation")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Conversation> addMessage(@ModelAttribute MessageDto messageDto) {
        try {
            return new ResponseEntity<>(conversationService.insert(messageDto), HttpStatus.OK);
        }catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/conversations")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Conversation> findByPerson(@RequestParam("personId1") String personId1, @RequestParam("personId2") String personId2) {
        return new ResponseEntity<>(conversationService.getConversations(personId1, personId2).get(0), HttpStatus.OK);
    }
}
