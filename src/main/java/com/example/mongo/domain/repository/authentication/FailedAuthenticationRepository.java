package com.example.mongo.domain.repository.authentication;

import com.example.mongo.domain.model.authentication.FailedAuthentication;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FailedAuthenticationRepository extends MongoRepository<FailedAuthentication, String> {
    long deleteByUsername(String username);
}
