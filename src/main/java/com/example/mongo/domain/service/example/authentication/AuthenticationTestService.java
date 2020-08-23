package com.example.mongo.domain.service.example.authentication;

import com.example.mongo.domain.model.authentication.LoggedInUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
public class AuthenticationTestService {

    @PreAuthorize("hasAnyRole('ADMIN')")
    public Boolean checkAuthenticationTest() {
        return true;
    }

    @PostAuthorize("returnObject == true")
    public Boolean checkAuthenticationTest2(LoggedInUser loggedInUser) {

        return loggedInUser.getAccount().getUsername().equals("tkotegawa");
    }

}
