package com.example.mongo.domain.repository.authentication;

import com.example.mongo.domain.model.authentication.PasswordHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PasswordHistoryRepository extends MongoRepository<PasswordHistory, String> {

        List<PasswordHistory> findByUsername(String username);

        List<PasswordHistory> findByUsernameAndUseFromAfter(String username, LocalDateTime useFrom);


}
