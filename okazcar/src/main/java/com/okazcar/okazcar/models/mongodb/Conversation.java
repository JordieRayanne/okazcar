package com.okazcar.okazcar.models.mongodb;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "conversation")
public class Conversation {
    @Field("conversation_id")
    @Id
    private String conversationId;

    @Field("person1")
    private Person person1;

    @Field("person2")
    private Person person2;

    @Field("messages")
    private List<Message> messages = new ArrayList<>();

    public Conversation(Person person1, Person person2, Message message) {
        this.person1 = person1;
        this.person2 = person2;
        this.messages.add(message);
    }
}


