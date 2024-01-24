package com.okazcar.okazcar.repositories.mongodb;

import com.okazcar.okazcar.models.mongodb.UserMongoDb;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserMongoDbRepository extends MongoRepository<UserMongoDb, String> {
    UserMongoDb findUserMongoDbByUserId(String userId);

    List<UserMongoDb> findUserMongoDbsByUserIdIn(List<String> userIds);
}
