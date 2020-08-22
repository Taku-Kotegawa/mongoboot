package com.example.mongo.domain.repository.authentication;

import com.example.mongo.domain.model.authentication.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {
}
