package com.example.mongo.domain.repository.authentication;

import com.example.mongo.domain.model.authentication.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository target;

    @BeforeEach
    void setUp() {
        target.deleteAll();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void test_insert() {
        target.save(Account.builder().username("1").build());
    }

}