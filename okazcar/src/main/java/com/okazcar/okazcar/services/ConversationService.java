package com.okazcar.okazcar.services;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.okazcar.okazcar.exception.ForgetException;
import com.okazcar.okazcar.models.HistoriqueMessage;
import com.okazcar.okazcar.models.Utilisateur;
import com.okazcar.okazcar.models.dto.MessageDto;
import com.okazcar.okazcar.models.mongodb.Conversation;
import com.okazcar.okazcar.models.mongodb.Message;
import com.okazcar.okazcar.models.mongodb.Person;
import com.okazcar.okazcar.repositories.HistoriqueMessageRepository;
import com.okazcar.okazcar.repositories.UtilisateurRepository;
import com.okazcar.okazcar.repositories.mongodb.ConversationRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class ConversationService {
    private final ConversationRepository conversationRepository;
    private final HistoriqueMessageService historiqueMessageService;
    private final HistoriqueMessageRepository historiqueMessageRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final NotificationService notificationService;

    Logger logger = LoggerFactory.getLogger(ConversationService.class);

    @Autowired
    public ConversationService(ConversationRepository conversationRepository,
                               HistoriqueMessageRepository historiqueMessageRepository, HistoriqueMessageService historiqueMessageService,
                               UtilisateurRepository utilisateurRepository,
                                NotificationService notificationService) {
        this.conversationRepository = conversationRepository;
        this.historiqueMessageService = historiqueMessageService;
        this.historiqueMessageRepository = historiqueMessageRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.notificationService= notificationService;
    }

    public Conversation insert(MessageDto messageDto) throws IOException, FirebaseMessagingException, FirebaseAuthException {
        Person person1 = new Person(messageDto.getPersonId1(), messageDto.getUsername1());
        Person person2 = new Person(messageDto.getPersonId2(), messageDto.getUsername2());
        List<HistoriqueMessage> count = historiqueMessageRepository.findHistoriqueMessagesFrom2Utilisateurs(messageDto.getPersonId1(), messageDto.getPersonId2());
        if (count.isEmpty()) {
            historiqueMessageService.insert(messageDto.getPersonId1(), messageDto.getPersonId2());
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "messages.dateTimeSend");
        List<Conversation> conversation = conversationRepository.findConversationByPersons(messageDto.getPersonId1(), messageDto.getPersonId2(), sort);
        Message message = new Message(messageDto);
        // <Notification>
        try {
            Utilisateur utilisateurReceiver = utilisateurRepository.findUtilisateurByUtilisateurId(messageDto.getPersonId2());
            notificationService.sendNotification(messageDto.getUsername1(), utilisateurReceiver, message);
        } catch (ForgetException e) {
            logger.error(e.getLocalizedMessage());
            logger.error(e.getCause().getMessage());
        }
        // </Notification>
        if (!conversation.isEmpty()) {
            Conversation conversationToSend = conversation.get(0);
            conversationToSend.getMessages().add(message);
            conversationRepository.save(conversationToSend);
            return conversationToSend;
        }
        Conversation newConversation = new Conversation(person1, person2, message);
        return conversationRepository.save(newConversation);
    }

    public List<Conversation> getConversations(String personId1, String personId2) {
        Sort sort = Sort.by(Sort.Direction.DESC, "messages.dateTimeSend");
        return conversationRepository.findConversationByPersons(personId1, personId2, sort);
    }
}
