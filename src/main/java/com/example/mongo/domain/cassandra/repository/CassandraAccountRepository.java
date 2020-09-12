package com.example.mongo.domain.cassandra.repository;

import com.example.mongo.domain.cassandra.model.CassandraAccount;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface CassandraAccountRepository extends CassandraRepository<CassandraAccount, String> {
}
