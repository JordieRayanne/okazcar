package com.okazcar.okazcar.repositories.mongodb;

import com.okazcar.okazcar.models.mongodb.Conversation;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;


public interface ConversationRepository extends MongoRepository<Conversation, String> {
    @Query(value = "{ $or: [ { 'person1.personId': ?0, 'person2.personId': ?1 }, { 'person1.personId': ?1, 'person2.personId': ?0 } ] }", count = true)
    List<Conversation> findConversationByPersons(String person1Id, String person2Id, Sort sort);
}
