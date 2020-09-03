package com.example.mongo.domain.service.authentication;

import com.example.mongo.app.common.StringUtils;
import com.example.mongo.app.common.datatables.Column;
import com.example.mongo.app.common.datatables.DataTablesInput;
import com.example.mongo.domain.model.authentication.Account;
import com.example.mongo.domain.repository.authentication.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<Account> findByDatatablesInput(DataTablesInput input) {

        // @see https://qiita.com/nishina555/items/9e20211e8d6f12fdb7b7
        // @see https://lishman.io/spring-data-mongotemplate-queries

        return mongoOperations.find(makeQuery(input, true), Account.class, "account");

    }

    @Override
    public Long countByDatatablesInput(DataTablesInput input) {
        return mongoOperations.count(makeQuery(input, false), Account.class, "account");
    }

    @Override
    public Long initMany(int num, int loopCount) {

        long totalCount = 0L;

        for (int j = 1; j <= loopCount; j++) {
            List<Account> accounts = new ArrayList<>();
            for (int i = 1; i <= num; i++) {
                String counter = StringUtils.leftPad(Integer.toString(j), 5, "0")
                        + StringUtils.leftPad(Integer.toString(i), 5, "0");
                if (!accountRepository.existsById("username" + counter)) {
                    accounts.add(
                            Account.builder()
                                    .username("username" + counter)
                                    .firstName("firstname" + counter)
                                    .lastName("lasttname" + counter)
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

        Query query = new Query();

        if (input == null) {
            return query;
        }

        if (onePage) {
            Pageable pageable = PageRequest.of(input.getStart() / input.getLength(), input.getLength());
            query.with(pageable);

            List<Sort.Order> orders = new ArrayList<>();
            for (com.example.mongo.app.common.datatables.Order datatablesOrder : input.getOrder()) {
                Sort.Direction dir = "asc".equals(datatablesOrder.getDir()) ? Sort.Direction.ASC : Sort.Direction.DESC;
                orders.add(new Order(dir, input.getColumns().get(datatablesOrder.getColumn()).getData()));
            }
            Sort sort = Sort.by(orders);
            query.with(sort);
        }

        List<Criteria> field = new ArrayList<>();
        for (Column column : input.getColumns()) {
            if (column.getSearchable() && !StringUtils.isEmpty(column.getSearch().getValue())) {
                field.add(Criteria.where(column.getData()).regex(column.getSearch().getValue()));
            }
        }
        if (!field.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(field.toArray(new Criteria[field.size()])));

        } else {

            String globalSearch = input.getSearch().getValue();

            List<Criteria> global = new ArrayList<>();
            if (!StringUtils.isEmpty(globalSearch)) {
                for (Column column : input.getColumns()) {
                    global.add(Criteria.where(column.getData()).regex(globalSearch));
                }
                query.addCriteria(new Criteria().orOperator(global.toArray(new Criteria[global.size()])));
            }
        }

        return query;
    }

}
