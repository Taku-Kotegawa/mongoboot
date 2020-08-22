package com.example.mongo.domain.service.authentication;

import com.example.mongo.domain.model.authentication.FailedAuthentication;
import com.example.mongo.domain.model.authentication.SuccessfulAuthentication;
import com.example.mongo.domain.repository.authentication.FailedAuthenticationRepository;
import com.example.mongo.domain.repository.authentication.SuccessfulAuthenticationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AuthenticationEventSharedServiceImpl implements AuthenticationEventSharedService {

    @Autowired
    SuccessfulAuthenticationRepository successfulAuthenticationRepository;

    @Autowired
    FailedAuthenticationRepository failedAuthenticationRepository;

    @Override
    public List<SuccessfulAuthentication> findLatestSuccessEvents(String username, int count) {
        return successfulAuthenticationRepository.findAll(
                Example.of(SuccessfulAuthentication.builder().username(username).build()),
                PageRequest.of(0, count, Sort.by(Sort.Direction.DESC, "authenticationTimestamp"))
        ).getContent();
    }

    @Override
    public List<FailedAuthentication> findLatestFailureEvents(String username, int count) {
        return failedAuthenticationRepository.findAll(
                Example.of(FailedAuthentication.builder().username(username).build()),
                PageRequest.of(0, count, Sort.by(Sort.Direction.DESC, "authenticationTimestamp"))
        ).getContent();
    }

    @Override
    public void authenticationSuccess(String username) {
        successfulAuthenticationRepository.insert(
                SuccessfulAuthentication.builder()
                        .username(username)
                        .build()
        );
        deleteFailureEventByUsername(username);
    }

    @Override
    public void authenticationFailure(String username) {
        failedAuthenticationRepository.insert(
                FailedAuthentication.builder()
                        .username(username)
                        .build()
        );
    }

    @Override
    public long deleteFailureEventByUsername(String username) {
        return failedAuthenticationRepository.deleteByUsername(username);
    }
}
