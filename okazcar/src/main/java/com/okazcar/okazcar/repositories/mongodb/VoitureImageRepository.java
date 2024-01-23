package com.okazcar.okazcar.repositories.mongodb;

import com.okazcar.okazcar.models.mongodb.VoitureImage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VoitureImageRepository extends MongoRepository<VoitureImage, String> {
}
