package com.example.mongo.domain.cassandra.repository;

import com.example.mongo.domain.cassandra.model.TestJpaEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(SpringExtension.class)
//@ContextConfiguration(locations = {"classpath:test-context.xml"})
class TestRepositoryTest {

    @Autowired
    TestRepository testRepository;

    @Test
    void findAll_001() {

//        Iterable<TestJpaEntity> list = testRepository.findAll();

    }
}