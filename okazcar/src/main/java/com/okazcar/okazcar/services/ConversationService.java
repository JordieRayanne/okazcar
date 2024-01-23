package com.okazcar.okazcar.services;

import com.okazcar.okazcar.models.dto.MessageDto;
import com.okazcar.okazcar.models.mongodb.Conversation;
import com.okazcar.okazcar.models.mongodb.Message;
import com.okazcar.okazcar.models.mongodb.Person;
import com.okazcar.okazcar.repositories.mongodb.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class ConversationService {
    private final ConversationRepository conversationRepository;
    @Autowired
    public ConversationService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public Conversation insert(MessageDto messageDto) throws IOException {
        Person person1 = new Person(messageDto.getPersonId1(), messageDto.getUsername1());
        Person person2 = new Person(messageDto.getPersonId2(), messageDto.getUsername2());
        Sort sort = Sort.by(Sort.Direction.DESC, "messages.dateTimeSend");
        List<Conversation> conversation = conversationRepository.findConversationByPersons(messageDto.getPersonId1(), messageDto.getPersonId2(), sort);
        Message message = new Message(messageDto);
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
