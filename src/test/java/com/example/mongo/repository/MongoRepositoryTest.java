package com.example.mongo.repository;


import com.example.mongo.domain.model.example.PersonPersistable;
import com.example.mongo.domain.repository.example.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:test-context.xml"})
public class MongoRepositoryTest {

    @Autowired
    PersonRepository target;

    @Autowired
    MongoOperations mongoOperations;

    private PersonPersistable createPerson(String id) {
        return new PersonPersistable(id, id + "_name", Integer.parseInt(id));
    }

    private void insertIntoDatabase(PersonPersistable... persons) {
        Collection<PersonPersistable> collection = new ArrayList<>();
        Collections.addAll(collection, persons);
        mongoOperations.insertAll(collection);
    }

    // -- insert --

    @Test
    @DisplayName("insert_001_[正常系]単一レコードの挿入")
    public void insert_001() {
        // -- 準備 --
        mongoOperations.dropCollection("personPersistable");
        PersonPersistable excepted = createPerson("1");

        // -- 実行 --
        PersonPersistable actual = target.insert(excepted);

        // -- 検証 --
        log.info(mongoOperations.find(new Query(), PersonPersistable.class).toString());
    }

    @Test
    @DisplayName("insert_002_[正常系]複数レコードの挿入")
    public void insert_002() {
        // -- 準備 --
        mongoOperations.dropCollection("personPersistable");
        List<PersonPersistable> excepted = new ArrayList<>();
        excepted.add(createPerson("1"));
        excepted.add(createPerson("2"));
        excepted.add(createPerson("3"));

        // -- 実行 --
        List<PersonPersistable> actual = target.insert(excepted);

        // -- 検証 --
        log.info(mongoOperations.find(new Query(), PersonPersistable.class).toString());
    }


    // -- save(insert) --

    @Test
    @DisplayName("save_001_[正常系]単一レコードの挿入")
    public void save_001() {
        // -- 準備 --
        mongoOperations.dropCollection("personPersistable");
        PersonPersistable excepted = createPerson("1");

        // -- 実行 --
        PersonPersistable actual = target.save(excepted);

        // -- 検証 --
        log.info(mongoOperations.find(new Query(), PersonPersistable.class).toString());
    }

    // -- save(update) --

    @Test
    @DisplayName("save_002_[正常系]単一レコードの更新")
    public void save_002() {
        // -- 準備 --
        mongoOperations.dropCollection("personPersistable");
        PersonPersistable excepted = createPerson("1");
        PersonPersistable actual = target.save(excepted);

        // -- 実行 --
        actual.setName("cnage_name");
        actual = target.save(actual);

        // -- 検証 --
        log.info(mongoOperations.find(new Query(), PersonPersistable.class).toString());
    }

    // -- saveAll() --

    @Test
    @DisplayName("saveAll_001_[正常系]複数レコードの挿入・更新")
    public void saveAll_001() {
        // -- 準備 --
        mongoOperations.dropCollection("personPersistable");
        List<PersonPersistable> excepted = new ArrayList<>();
        excepted.add(createPerson("1"));
        excepted.add(createPerson("2"));
        excepted.add(createPerson("3"));

        // -- 実行 --
        List<PersonPersistable> actual = target.saveAll(excepted);

        // -- 検証 --
        log.info(mongoOperations.find(new Query(), PersonPersistable.class).toString());

        // -- 実行 --
        actual = target.saveAll(actual);

        // -- 検証 --
        log.info(mongoOperations.find(new Query(), PersonPersistable.class).toString());
    }

    @Test
    @DisplayName("findAll_001_[正常系]")
    public void findAll_001() {
        // -- 準備 --
        mongoOperations.dropCollection("personPersistable");
        List<PersonPersistable> excepted = new ArrayList<>();
        insertIntoDatabase(
                new PersonPersistable("1", "same_name", 12),
                new PersonPersistable("2", "same_name", 11),
                new PersonPersistable("3", "different_name", 10)
        );

        // -- findAll() --
        // -- 実行 --
        List<PersonPersistable> actual = target.findAll();
        // -- 検証 --
        log.info("findAll()");
        log.info(actual.toString());

        // -- findAll(Sort sort) --
        // -- 実行 --
        Sort sort = Sort.by(Sort.Direction.DESC, "age", "name");
        actual = target.findAll(sort);
        // -- 検証 --
        log.info("findAll(Sort sort)");
        log.info(actual.toString());

        // -- findAll(Example<S> example) --
        PersonPersistable prob = new PersonPersistable();
        prob.setName("different_name");
        Example<PersonPersistable> example = Example.of(prob);
        actual = target.findAll(example);
        // -- 検証 --
        log.info("findAll(Example<S> example)");
        log.info(actual.toString());

        // -- findAll(Example<S> example, Sort sort) --
        prob = new PersonPersistable();
        prob.setName("same_name");
        example = Example.of(prob);
        actual = target.findAll(example, sort);
        log.info("findAll(Example<S> example, Sort sort)");
        log.info(actual.toString());

        // -- findAll(Pageable pageable) --
        Pageable page = PageRequest.of(1, 2, Sort.by("name"));
        Page<PersonPersistable> actual2 = target.findAll(page);
        log.info("findAll(Pageable pageable)");


        // -- findAll(Example<S> example, Pageable pageable) --
        actual2 = target.findAll(example, page);
        log.info("findAll(Pageable pageable)");


    }

    // -- findAllById(Iterable<ID> iterable) --
    @Test
    @DisplayName("findAllById_001_[正常系]")
    public void findAllById_001() {
        // -- 準備 --
        mongoOperations.dropCollection("personPersistable");
        List<PersonPersistable> excepted = new ArrayList<>();
        insertIntoDatabase(
                new PersonPersistable("1", "same_name", 12),
                new PersonPersistable("2", "same_name", 11),
                new PersonPersistable("3", "different_name", 10)
        );

        // -- 実行 --
        List<String> ids = new ArrayList<>();
        ids.add("1");
        ids.add("2");

        Iterable<PersonPersistable> actual = target.findAllById(ids);
        // -- 検証 --
        log.info("findAllById(Iterable<ID> iterable)");
        log.info(actual.toString());

    }

    // -- findById(String Id) --
    @Test
    @DisplayName("findById_001_[正常系]")
    public void findById_001() {
        // -- 準備 --
        mongoOperations.dropCollection("personPersistable");
        List<PersonPersistable> excepted = new ArrayList<>();
        insertIntoDatabase(
                new PersonPersistable("1", "same_name", 12),
                new PersonPersistable("2", "same_name", 11),
                new PersonPersistable("3", "different_name", 10)
        );

        // -- 実行 --
        Optional<PersonPersistable> actual = target.findById("2");
        // -- 検証 --
        log.info("findById(String Id)");
        log.info(actual.toString());

    }

    // -- findOne(Example<S> example) --
    @Test
    @DisplayName("findOne_001_[正常系]")
    public void findOne_001() {
        // -- 準備 --
        mongoOperations.dropCollection("personPersistable");
        List<PersonPersistable> excepted = new ArrayList<>();
        insertIntoDatabase(
                new PersonPersistable("1", "same_name", 12),
                new PersonPersistable("2", "same_name", 11),
                new PersonPersistable("3", "different_name", 10)
        );

        // -- 実行 --
        PersonPersistable prob = new PersonPersistable();
        prob.setName("same_name");
        Example<PersonPersistable> example = Example.of(prob);
        Optional<PersonPersistable> actual = target.findOne(example);
        // -- 検証 --
        log.info("findOne(Example<S> example)");
        log.info(actual.toString());
    }

    // -- count() --
    // -- count(Example<S> example) --
    // -- delete(PersonPersistable t) --
    // -- deleteAll() --
    // -- deleteAll() --
    // -- deleteById(String Id) --
    // -- exists(Example<S> example) --
    // -- existsById(String Id) --



    // -- カスタムメソッド --

    // -- findByNameAndAge --
    @Test
    @DisplayName("findByNameAndAge_001_[正常系]")
    public void findByNameAndAge_001() {
        // -- 準備 --
        mongoOperations.dropCollection("personPersistable");
        List<PersonPersistable> excepted = new ArrayList<>();
        insertIntoDatabase(
                new PersonPersistable("1", "same_name", 12),
                new PersonPersistable("2", "same_name", 11),
                new PersonPersistable("3", "different_name", 10)
        );

        // -- 実行 --
        List<PersonPersistable> actual = target.findByNameAndAge("same_name", 11);
        // -- 検証 --
        log.info("findByNameAndAge");
        log.info(actual.toString());
    }



}
