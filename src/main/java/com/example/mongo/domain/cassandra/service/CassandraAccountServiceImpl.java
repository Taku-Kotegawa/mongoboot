package com.example.mongo.domain.cassandra.service;

import com.example.mongo.app.common.StringUtils;
import com.example.mongo.app.common.datatables.Column;
import com.example.mongo.app.common.datatables.DataTablesInput;
import com.example.mongo.app.common.datatables.Order;
import com.example.mongo.domain.cassandra.model.CassandraAccount;
import com.example.mongo.domain.cassandra.repository.CassandraAccountRepository;
import com.example.mongo.domain.cassandra.repository.TestRepository;
import com.example.mongo.domain.model.authentication.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.cassandra.core.query.CriteriaDefinition;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CassandraAccountServiceImpl implements CassandraAccountService {

    @Autowired
    TestRepository testRepository;

    @Autowired
    CassandraAccountRepository accountRepository;

    @Autowired
    CassandraOperations template;



    @Override
    public List<CassandraAccount> findByDatatablesInput(DataTablesInput input) {

        Slice<CassandraAccount> slice = template.slice(makeQuery(input, true), CassandraAccount.class);

        return slice.getContent();

    }

    @Override
    public Long countByDatatablesInput(DataTablesInput input) {
        return template.count(makeQuery(input, false), CassandraAccount.class);
    }

    @Override
    public Long initMany(int num, int loopCount) {
        long totalCount = 0L;

        for (int j = 1; j <= loopCount; j++) {
            List<CassandraAccount> accounts = new ArrayList<>();
            for (int i = 1; i <= num; i++) {
                String counter = StringUtils.leftPad(Integer.toString(j), 5, "0")
                        + StringUtils.leftPad(Integer.toString(i), 5, "0");
                if (!accountRepository.existsById("username" + counter)) {
                    accounts.add(
                            CassandraAccount.builder()
                                    .username("username" + counter)
                                    .firstName("firstname" + StringUtils.leftPad(Integer.toString(j), 5, "0"))
                                    .lastName("lasttname" + StringUtils.leftPad(Integer.toString(i), 5, "0"))
                                    .email("email" + counter + "@stnet.co.jp")
                                    .profile("profile" + counter)
                                    .url("www" + counter + ".stnet.co.jp")
                                    .password("dummy")
                                    .build()
                    );
                    totalCount++;
                }
            }
            accountRepository.insert(accounts);
        }

        return totalCount;
    }

    private Query makeQuery(DataTablesInput input, boolean onePage) {


        if (input == null) {
            return Query.empty();
        }


        List<CriteriaDefinition> field = new ArrayList<>();
        for (Column column : input.getColumns()) {
            if (column.getSearchable() && !StringUtils.isEmpty(column.getSearch().getValue())) {
                    field.add(Criteria.where(column.getData()).is(column.getSearch().getValue()));
            }
        }

        List<Sort.Order> orders = new ArrayList<>();
        for (Order datatablesOrder : input.getOrder()) {
            Sort.Direction dir = "asc".equals(datatablesOrder.getDir()) ? Sort.Direction.ASC : Sort.Direction.DESC;
            orders.add(new Sort.Order(dir, input.getColumns().get(datatablesOrder.getColumn()).getData()));
        }
        Sort sort = Sort.by(orders);


        if (onePage) {
            Pageable pageable = CassandraPageRequest.of(input.getStart() / input.getLength(), input.getLength());
//          return Query.query(field).sort(sort).pageRequest(pageable);
            return Query.query(field).pageRequest(pageable).limit(input.getLength());

        } else {
            return Query.query(field).withAllowFiltering().limit(input.getLength());
        }

    }

}
