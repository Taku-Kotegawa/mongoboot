package com.example.mongo.domain.repository.authentication;

import com.example.mongo.domain.model.authentication.FailedPasswordReissue;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FailedPasswordReissueRepository extends MongoRepository<FailedPasswordReissue, String> {

    List<FailedPasswordReissue> findByToken(String token);
    List<FailedPasswordReissue> deleteByToken(String token);
    long countByToken(String token);

    List<FailedPasswordReissue> deleteByAttemptDateLessThan(LocalDateTime attemptDate);

}
