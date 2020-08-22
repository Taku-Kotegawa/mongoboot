package com.example.mongo.domain.repository.authentication;

import com.example.mongo.domain.model.authentication.SuccessfulAuthentication;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SuccessfulAuthenticationRepository extends MongoRepository<SuccessfulAuthentication, String> {
}
