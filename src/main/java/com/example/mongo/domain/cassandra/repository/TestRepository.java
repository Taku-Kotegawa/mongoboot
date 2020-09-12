package com.example.mongo.domain.cassandra.repository;

import com.example.mongo.domain.cassandra.model.TestJpaEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface TestRepository extends CassandraRepository<TestJpaEntity, String> {

}