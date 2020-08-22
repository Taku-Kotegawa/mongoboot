package com.example.mongo.domain.repository.authentication;

import com.example.mongo.domain.model.authentication.AccountImage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountImageRepository extends MongoRepository<AccountImage, String> {
}
