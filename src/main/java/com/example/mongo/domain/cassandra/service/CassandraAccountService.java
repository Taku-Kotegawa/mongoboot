package com.example.mongo.domain.cassandra.service;

import com.example.mongo.app.common.datatables.DataTablesInput;
import com.example.mongo.domain.cassandra.model.CassandraAccount;
import com.example.mongo.domain.model.authentication.Account;

import java.util.List;

public interface CassandraAccountService {
    List<CassandraAccount> findByDatatablesInput(DataTablesInput input);
    Long countByDatatablesInput(DataTablesInput input);
    Long initMany(int num, int loopCount);
}
