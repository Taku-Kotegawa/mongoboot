package com.example.mongo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.config.MongoConfigurationSupport;

/*
 * @see https://www.baeldung.com/spring-data-mongodb-tutorial
 */
@Configuration
@EnableMongoAuditing
//@EnableMongoRepositories(basePackages = "com.example.mongo.domain.repository")
public class MongoConfig extends MongoConfigurationSupport {
    @Override
    protected String getDatabaseName() {
        return null;
    }
}
