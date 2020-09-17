package com.example.mongo.domain.elasticsearch.service;

import com.example.mongo.app.common.StringUtils;
import com.example.mongo.app.common.datatables.Column;
import com.example.mongo.app.common.datatables.DataTablesInput;
import com.example.mongo.app.common.datatables.Order;
import com.example.mongo.domain.elasticsearch.model.Article;
import com.example.mongo.domain.elasticsearch.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;

@Slf4j
@Service
public class EsService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ElasticsearchOperations operations;

    public void initMany(int num, int loopCount) {

        log.info("Data Create Start");

        long totalCount = 0L;

        for (int j = 1; j <= loopCount; j++) {
            List<Article> accounts = new ArrayList<>();
            for (int i = 1; i <= num; i++) {
                String counter = StringUtils.leftPad(Integer.toString(j), 5, "0")
                        + StringUtils.leftPad(Integer.toString(i), 5, "0");
                if (!articleRepository.existsById("username" + counter)) {
                    accounts.add(
                            Article.builder()
                                    .title("title" + counter)
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
            articleRepository.saveAll(accounts);
        }

        log.info("Data Create End");

    }


    public SearchHits<Article> findByDatatablesInput(DataTablesInput input) {
        return operations.search(makeQuery(input, true), Article.class, IndexCoordinates.of("blog"));
    }

    public Long countAll() {
        return operations.count(Query.findAll(), IndexCoordinates.of("blog"));
    }



    private SortOrder getSortOder(Order datatablesOrder) {
        return "asc".equals(datatablesOrder.getDir()) ? SortOrder.ASC : SortOrder.DESC;
    }


    private Query makeQuery(DataTablesInput input, boolean onePage) {


        // -- Page --
        Pageable pageable = PageRequest.of(input.getStart() / input.getLength(), input.getLength());

        // -- Sort --
        FieldSortBuilder sortBuilder = null;
        for (com.example.mongo.app.common.datatables.Order datatablesOrder : input.getOrder()) {
            String fieldName = input.getColumns().get(datatablesOrder.getColumn()).getData();
            sortBuilder = SortBuilders.fieldSort(fieldName).order(getSortOder(datatablesOrder));
        }

        // -- Filter --
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        for (Column column : input.getColumns()) {
            if (column.getSearchable() && !StringUtils.isEmpty(column.getSearch().getValue())) {
                boolQueryBuilder.must(wildcardQuery(column.getData(), "*" + column.getSearch().getValue() + "*"));
            }
        }

//        String globalSearch = input.getSearch().getValue();
//        if (queryBuilder == null && !StringUtils.isEmpty(globalSearch)) {
//            for (Column column : input.getColumns()) {
//                global.add(Criteria.where(column.getData()).regex(globalSearch));
//            }
//        }

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(boolQueryBuilder)
                .withSort(sortBuilder)
                .build();

        return searchQuery;
    }

    public Long countByDatatablesInput2(DataTablesInput input) {
        return operations.count(makeQuery(input), Article.class, IndexCoordinates.of("blog"));
    }


    public SearchHits<Article> findByDatatablesInput2(DataTablesInput input) {
        return operations.search(makeQuery(input), Article.class, IndexCoordinates.of("blog"));
    }


    private CriteriaQuery makeQuery(DataTablesInput input) {
        // -- Page --
        Pageable pageable = PageRequest.of(input.getStart() / input.getLength(), input.getLength());

        // -- Sort --
        List<Sort.Order> orders = new ArrayList<>();
        for (com.example.mongo.app.common.datatables.Order datatablesOrder : input.getOrder()) {
            Sort.Direction dir = "asc".equals(datatablesOrder.getDir()) ? Sort.Direction.ASC : Sort.Direction.DESC;
            orders.add(new Sort.Order(dir, input.getColumns().get(datatablesOrder.getColumn()).getData()));
        }
        Sort sort = Sort.by(orders);


        Criteria criteria = new Criteria();
        for (Column column : input.getColumns()) {
            if (column.getSearchable() && !StringUtils.isEmpty(column.getSearch().getValue())) {
                criteria = criteria.and(column.getData()).contains(column.getSearch().getValue());
            }
        }

        String globalSearch = input.getSearch().getValue();
        if (StringUtils.isNotEmpty(globalSearch)) {
            Criteria global = new Criteria();
            for (Column column : input.getColumns()) {
                global = global.or(column.getData()).contains(globalSearch);
            }
            criteria = criteria.and(global);
        }

        CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);
        criteriaQuery.setPageable(pageable);
//        criteriaQuery.addSort(sort);

        return criteriaQuery;
    }



}
