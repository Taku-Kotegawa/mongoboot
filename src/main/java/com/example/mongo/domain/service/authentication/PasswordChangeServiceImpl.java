package com.example.mongo.domain.service.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PasswordChangeServiceImpl implements PasswordChangeService {

    @Autowired
    AccountSharedService accountSharedService;

    @Override
    public boolean updatePassword(String username, String rawPassword) {
        return accountSharedService.updatePassword(username, rawPassword);
    }
}
