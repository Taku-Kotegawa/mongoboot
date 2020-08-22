package com.example.mongo.domain.repository.authentication;

import com.example.mongo.domain.model.authentication.PasswordReissueInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PasswordReissueInfoRepository extends MongoRepository<PasswordReissueInfo, String> {

    List<PasswordReissueInfo> findByExpiryDateLessThan(LocalDateTime date);
    List<PasswordReissueInfo> deleteByExpiryDateLessThan(LocalDateTime date);
}
