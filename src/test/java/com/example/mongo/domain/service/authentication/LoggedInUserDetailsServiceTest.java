package com.example.mongo.domain.service.authentication;

import com.example.mongo.domain.model.authentication.Account;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LoggedInUserDetailsServiceTest {

    @Autowired
    LoggedInUserDetailsService target;

    @Autowired
    AccountSharedService accountSharedService;

    // ---- ヘルパー関数 ----
    private Account createAccount(String username) {
        return Account.builder()
                .roles(Lists.newArrayList("USER"))
                .username(username)
                .password("Password:" + username)
                .firstName("FirstName:" + username)
                .lastName("LastName:" + username)
                .email("Email:" + username)
                .url("Url:" + username)
                .profile("Profile:" + username)
                .build();
    }

    @Test
    @DisplayName("正常系")
    void loadUserByUsername_001() {
        // ---- 準備 ----
        accountSharedService.create(createAccount("user1"), null);
        // ---- 実行 ----
        UserDetails user = target.loadUserByUsername("user1");
        // ---- 検証 ----
        assertThat(user).isNotNull();
    }
}