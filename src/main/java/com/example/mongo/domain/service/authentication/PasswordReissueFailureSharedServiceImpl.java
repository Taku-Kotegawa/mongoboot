package com.example.mongo.domain.service.authentication;

import com.example.mongo.domain.model.authentication.FailedPasswordReissue;
import com.example.mongo.domain.repository.authentication.FailedPasswordReissueRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class PasswordReissueFailureSharedServiceImpl implements PasswordReissueFailureSharedService {

    @Autowired
    FailedPasswordReissueRepository failedPasswordReissueRepository;

    @Override
    public void resetFailure(String username, String token) {
        failedPasswordReissueRepository.insert(
                FailedPasswordReissue.builder().token(token).attemptDate(LocalDateTime.now()).build()
        );
    }
}
