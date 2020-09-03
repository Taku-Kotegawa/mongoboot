package com.example.mongo.domain.repository.example;


import com.example.mongo.domain.model.example.PersonPersistable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface PersonRepository extends MongoRepository<PersonPersistable, String> {

    // @see https://docs.spring.io/spring-data/mongodb/docs/2.2.9.RELEASE/reference/html/#mongo.repositories


    List<PersonPersistable> findByName(String name);

    List<PersonPersistable> findByNameAndAge(String name, Integer age);



}
