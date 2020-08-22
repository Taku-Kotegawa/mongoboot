package com.example.mongo.domain.service.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UnlockServiceImpl implements UnlockService {

    @Autowired
    AuthenticationEventSharedService authenticationEventSharedService;

    @Override
    public void unlock(String username) {
        authenticationEventSharedService.deleteFailureEventByUsername(username);
    }
}
