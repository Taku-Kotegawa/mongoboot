package com.example.mongo.domain.service.authentication;

import com.example.mongo.app.common.datatables.DataTablesInput;
import com.example.mongo.domain.model.authentication.Account;

import java.util.List;

public interface AccountService {

    List<Account> findByDatatablesInput(DataTablesInput input);
    Long countByDatatablesInput(DataTablesInput input);
    Long initMany(int num, int loopCount);
}
