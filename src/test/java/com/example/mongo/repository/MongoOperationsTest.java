package com.example.mongo.repository;



import com.example.mongo.domain.model.example.Person;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:test-context.xml"})
public class MongoOperationsTest {

    @Autowired
    MongoOperations mongoOperations;

    private Person createPerson(String id) {
        return new Person(id, id + "_name", Integer.parseInt(id));
    }

    private void insertIntoDatabase(Person... persons) {
        Collection<Person> collection = new ArrayList<>();
        Collections.addAll(collection, persons);
        mongoOperations.insertAll(collection);
    }

    // -- insert --

    @Test
    @DisplayName("insert_001_[正常系]単一レコードの挿入")
    public void insert_001() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        Person excepted = createPerson("1");

        // -- 実行 --
        Person actual = mongoOperations.insert(excepted);

        // -- 検証 --
        log.info(actual.toString());
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(excepted.getName());
        assertThat(actual.getAge()).isEqualTo(excepted.getAge());
    }

    @Test
    @DisplayName("insert_002_[異常系]一意制約違反")
    public void insert_002() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        Person excepted = createPerson("1");
        mongoOperations.insert(excepted);

        // -- 実行 --
        assertThatThrownBy(() -> {
            mongoOperations.insert(excepted);
        })
                // ---- 検証 ----
                .isInstanceOf(DuplicateKeyException.class);
    }


    // -- insertAll --

    @Test
    @DisplayName("insertAll_001_[正常系]複数レコードの一括挿入")
    public void insertAll_001() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        List<Person> personList = new ArrayList<>();
        personList.add(createPerson("1"));
        personList.add(createPerson("2"));
        personList.add(createPerson("3"));

        // -- 実行 --
        Collection<Person> actual = mongoOperations.insertAll(personList);

        // -- 検証 --
        log.info(actual.toString());
        assertThat(actual).hasSize(3);

    }


    // -- findById --

    @Test
    @DisplayName("findById_001_[正常系]キーで検索")
    public void findById_001() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                createPerson("1"),
                createPerson("2"),
                createPerson("3")
        );

        // -- 実行 --
        Person actual = mongoOperations.findById("1", Person.class);

        // -- 検証 --
        log.info(actual.toString());
        assertThat(actual.getName()).isEqualTo("1_name");
    }

    // -- findOne --

    @Test
    @DisplayName("findOne_001_[正常系]is()で検索")
    public void findOne_001() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                createPerson("1"),
                createPerson("2"),
                createPerson("3")
        );

        // -- 実行 --
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("2_name"));
        Person actual = mongoOperations.findOne(query, Person.class);

        // -- 検証 --
        log.info(actual.toString());
        assertThat(actual.getName()).isEqualTo("2_name");
    }

    @Test
    @DisplayName("findOne_002_[正常系]無条件で検索")
    public void findOne_002() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                createPerson("1"),
                createPerson("2"),
                createPerson("3")
        );

        // -- 実行 --
        Query query = new Query();
        Person actual = mongoOperations.findOne(query, Person.class);

        // -- 検証 --
        log.info(actual.toString());
        // 条件に合致した最初の１件を取得している様子
        assertThat(actual.getName()).isEqualTo("1_name");
    }

    @Test
    @DisplayName("findOne_003_[正常系]正規表現(regex)で検索")
    public void findOne_003() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                createPerson("1"),
                createPerson("2"),
                createPerson("3")
        );

        // -- 実行 --
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex("^1"));
        Person actual = mongoOperations.findOne(query, Person.class);

        // -- 検証 --
        log.info(actual.toString());
        // 条件に合致した最初の１件を取得している様子
        assertThat(actual.getName()).isEqualTo("1_name");
    }

    // -- find --

    @Test
    @DisplayName("find_001_[正常系]条件を指定した検索")
    public void find_001() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                createPerson("1"),
                createPerson("2"),
                createPerson("3")
        );

        // -- 実行 --
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex("name$"));
        Collection<Person> actual = mongoOperations.find(query, Person.class);

        // -- 検証 --
        log.info(actual.toString());
        assertThat(actual).hasSize(3);
    }

    @Test
    @DisplayName("find_002_[正常系]条件を指定した検索")
    public void find_002() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                createPerson("1"),
                createPerson("2"),
                createPerson("3")
        );

        // -- 実行 --
        Query query = new Query();
        query.addCriteria(Criteria.where("age").gt(1));
        Collection<Person> actual = mongoOperations.find(query, Person.class);

        // -- 検証 --
        log.info(actual.toString());
        assertThat(actual).hasSize(2);
    }

    @Test
    @DisplayName("find_003_[正常系]条件を指定した検索")
    public void find_003() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                createPerson("1"),
                createPerson("2"),
                createPerson("3")
        );

        // -- 実行 --
        Query query = new Query();
        query.addCriteria(Criteria.where("age").gt(1).lt(3));
        Collection<Person> actual = mongoOperations.find(query, Person.class);

        // -- 検証 --
        log.info(actual.toString());
        assertThat(actual).hasSize(1);
    }

    @Test
    @DisplayName("find_004_[正常系]条件を指定した検索")
    public void find_004() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                createPerson("1"),
                createPerson("2"),
                createPerson("3")
        );

        // -- 実行 --
        Query query = new Query();
        query.addCriteria(Criteria.where("age").gt(1)); // 2件
        query.addCriteria(Criteria.where("name").regex("name$")); // 3件
        Collection<Person> actual = mongoOperations.find(query, Person.class);

        // -- 検証 --
        log.info(actual.toString());
        assertThat(actual).hasSize(2);
    }


    @Test
    @DisplayName("find_005_[正常系]ソートを指定した検索(複合条件)")
    public void find_005() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                new Person("1", "bbb", 1),
                new Person("2", "aaa", 2),
                new Person("3", "aaa", 1)
        );

        // -- 実行 --
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.ASC, "name")
                .and(Sort.by(Sort.Direction.ASC, "age")));


        List<Person> actual = mongoOperations.find(query, Person.class);

        // -- 検証 --
        log.info(actual.toString());
        assertThat(actual.get(0).getId()).isEqualTo("3");
        assertThat(actual.get(1).getId()).isEqualTo("2");
        assertThat(actual.get(2).getId()).isEqualTo("1");
    }

    // -- findAll --

    @Test
    @DisplayName("findAll_001_[正常系]全件取得")
    public void findAll_001() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                createPerson("1"),
                createPerson("2"),
                createPerson("3")
        );

        // -- 実行 --
        List<Person> actual = mongoOperations.findAll(Person.class);

        // -- 検証 --
        log.info(actual.toString());
        assertThat(actual).hasSize(3);
    }


    // -- save --

    @Test
    @DisplayName("save_001_[正常系]データ更新")
    public void save_001() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                createPerson("1"),
                createPerson("2"),
                createPerson("3")
        );

        Person expected = new Person("1", "change", 999);

        // -- 実行 --
        Person actual = mongoOperations.save(expected);

        // -- 検証 --
        log.info(actual.toString());
        assertThat(actual).isEqualTo(expected);
    }


    @Test
    @DisplayName("save_002_[正常系]データ挿入")
    public void save_002() {

        // -- 準備 --
        mongoOperations.dropCollection("person");

        Person expected = new Person("1", "change", 999);

        // -- 実行 --
        Person actual = mongoOperations.save(expected);

        // -- 検証 --
        log.info(actual.toString());
        assertThat(actual).isEqualTo(expected);
    }

    // -- updateFirst --

    @Test
    @DisplayName("updateFirst_001_[正常系]条件に合致する最初の１件の更新")
    public void updateFirst_001() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                createPerson("1"),
                createPerson("2"),
                createPerson("3")
        );

        // -- 実行 --
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex("name$"));
        Update update = new Update();
        update.set("name", "changed_name");

        UpdateResult actual = mongoOperations.updateFirst(query, update, Person.class);

        // -- 検証 --
        log.info(actual.toString());
        assertThat(actual.getModifiedCount()).isEqualTo(1);

        List<Person> personList = mongoOperations.find(new Query()
                .addCriteria(Criteria.where("name").is("changed_name")), Person.class);
        log.info(personList.toString());
        assertThat(personList).hasSize(1);

    }

    @Test
    @DisplayName("updateFirst_002_[正常系]条件に合致する更新対象なし")
    public void updateFirst_002() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                createPerson("1"),
                createPerson("2"),
                createPerson("3")
        );

        // -- 実行 --
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex("xxx$"));
        Update update = new Update();
        update.set("name", "changed_name");

        UpdateResult actual = mongoOperations.updateFirst(query, update, Person.class);

        // -- 検証 --
        log.info(actual.toString());
        assertThat(actual.getModifiedCount()).isEqualTo(0);
    }


    // -- updateMulti --

    @Test
    @DisplayName("updateMulti_001_[正常系]条件に合致する全件の更新")
    public void updateMulti_001() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                createPerson("1"),
                createPerson("2"),
                createPerson("3")
        );

        // -- 実行 --
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex("name$"));
        Update update = new Update();
        update.set("name", "changed_name");

        UpdateResult actual = mongoOperations.updateMulti(query, update, Person.class);

        // -- 検証 --
        log.info(actual.toString());
        assertThat(actual.getModifiedCount()).isEqualTo(3);

        List<Person> personList = mongoOperations.find(new Query()
                .addCriteria(Criteria.where("name").is("changed_name")), Person.class);
        log.info(personList.toString());
        assertThat(personList).hasSize(3);

    }

    @Test
    @DisplayName("updateMulti_002_[正常系]条件に合致する更新対象なし")
    public void updateMulti_002() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                createPerson("1"),
                createPerson("2"),
                createPerson("3")
        );

        // -- 実行 --
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex("xxxx$"));
        Update update = new Update();
        update.set("name", "changed_name");

        UpdateResult actual = mongoOperations.updateMulti(query, update, Person.class);

        // -- 検証 --
        log.info(actual.toString());
        assertThat(actual.getModifiedCount()).isEqualTo(0);

    }

    // -- upsert --

    @Test
    @DisplayName("upsert_001_[正常系]条件に合致する１件の更新")
    public void upsert_001() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                createPerson("1"),
                createPerson("2"),
                createPerson("3")
        );

        // -- 実行 --
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex("name$"));
        Update update = new Update();
        update.set("name", "changed_name");

        UpdateResult actual = mongoOperations.upsert(query, update, Person.class);

        // -- 検証 --
        log.info(actual.toString());
        assertThat(actual.getModifiedCount()).isEqualTo(1);

        List<Person> personList = mongoOperations.find(new Query()
                .addCriteria(Criteria.where("name").is("changed_name")), Person.class);
        log.info(personList.toString());
        assertThat(personList).hasSize(1);

    }

    @Test
    @DisplayName("upsert_002_[正常系]条件に合致する更新対象なし->新規登録される")
    public void upsert_002() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                createPerson("1"),
                createPerson("2"),
                createPerson("3")
        );

        // -- 実行 --
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex("xxxx$"));
        Update update = new Update();
        update.set("name", "changed_name");

        UpdateResult actual = mongoOperations.upsert(query, update, Person.class);

        // -- 検証 --
        log.info(actual.toString());
        assertThat(actual.getModifiedCount()).isEqualTo(0);

        List<Person> personList = mongoOperations.find(new Query()
                .addCriteria(Criteria.where("name").is("changed_name")), Person.class);
        log.info(personList.toString());

    }

    // -- findAndModify --

    @Test
    @DisplayName("findAndModify_001_[正常系]条件に合致する１件の更新")
    public void findAndModify_001() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                createPerson("1"),
                createPerson("2"),
                createPerson("3")
        );

        // -- 実行 --
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex("name$"));
        Update update = new Update();
        update.set("name", "changed_name");

        Person person = mongoOperations.findAndModify(query, update, Person.class);

        // -- 検証 --
        log.info(person.toString());
        assertThat(person.getId()).isEqualTo("1");

        log.info(mongoOperations.find(new Query(), Person.class).toString());

    }

    // -- findAndReplace --

    @Test
    @DisplayName("findAndReplace_001_[正常系]条件に合致する１件の更新")
    public void findAndReplace_001() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                createPerson("1"),
                createPerson("2"),
                createPerson("3")
        );

        Person replace = new Person("1", "replace_name", 99);

        // -- 実行 --
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex("name$"));

        Person before = mongoOperations.findAndReplace(query, replace);

        // -- 検証 --
        log.info(mongoOperations.find(new Query(), Person.class).toString());

        assertThat(before).isEqualTo(createPerson("1")); // 戻り値は更新前のデータ

        Person actual = mongoOperations.findById("1", Person.class);
        assertThat(actual.getName()).isEqualTo("replace_name");

    }

    @Test
    @DisplayName("findAndReplace_002_[異常系]IDを更新するとエラー")
    public void findAndReplace_002() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                createPerson("1"),
                createPerson("2"),
                createPerson("3")
        );

        Person replace = new Person("999", "replace_name", 99);

        // -- 実行 --
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex("name$"));

        assertThatThrownBy(() -> {
            mongoOperations.findAndReplace(query, replace);
        })
                // ---- 検証 ----
                .isInstanceOf(InvalidDataAccessApiUsageException.class)
                .hasMessageContaining("66");

        log.info(mongoOperations.find(new Query(), Person.class).toString());

    }


    // -- remove --

    @Test
    @DisplayName("remove_001_[正常系]条件に合致する１件の削除")
    public void remove_001() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                createPerson("1"),
                createPerson("2"),
                createPerson("3")
        );

        // -- 実行 --
        Query query = new Query();
        query.addCriteria(Criteria.where("id").regex("1"));

        DeleteResult actual = mongoOperations.remove(query, Person.class);

        // -- 検証 --
        log.info(actual.toString());
        assertThat(actual.getDeletedCount()).isEqualTo(1);

        List<Person> personList = mongoOperations.find(new Query(), Person.class);
        log.info(personList.toString());
        assertThat(personList).hasSize(2);

    }

    @Test
    @DisplayName("remove_002_[正常系]条件に合致する全件の削除")
    public void remove_002() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                createPerson("1"),
                createPerson("2"),
                createPerson("3")
        );

        // -- 実行 --
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex("name$"));

        DeleteResult actual = mongoOperations.remove(query, Person.class);

        // -- 検証 --
        log.info(actual.toString());
        assertThat(actual.getDeletedCount()).isEqualTo(3);

        List<Person> personList = mongoOperations.find(new Query(), Person.class);
        log.info(personList.toString());
        assertThat(personList).hasSize(0);

    }

    // -- findAndRemove --

    @Test
    @DisplayName("findAndRemove_001_[正常系]条件に合致する１件の削除、削除したデータの取得")
    public void findAndRemove_001() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                createPerson("1"),
                createPerson("2"),
                createPerson("3")
        );

        // -- 実行 --
        Query query = new Query();
        query.addCriteria(Criteria.where("id").regex("1"));

        Person person = mongoOperations.findAndRemove(query, Person.class);

        // -- 検証 --
        log.info(person.toString());
        assertThat(person.getId()).isEqualTo("1");

        List<Person> personList = mongoOperations.find(new Query(), Person.class);
        log.info(personList.toString());
        assertThat(personList).hasSize(2);
    }

    @Test
    @DisplayName("findAndRemove_001_[正常系]条件に合致する最初の１件の削除、削除したデータの取得")
    public void findAndRemove_002() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                createPerson("1"),
                createPerson("2"),
                createPerson("3")
        );

        // -- 実行 --
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex("name$"));

        Person person = mongoOperations.findAndRemove(query, Person.class);

        // -- 検証 --
        log.info(person.toString());
        assertThat(person.getId()).isEqualTo("1");

        List<Person> personList = mongoOperations.find(new Query(), Person.class);
        log.info(personList.toString());
        assertThat(personList).hasSize(2);

    }

    // -- findAllAndRemove --

    @Test
    @DisplayName("findAllAndRemove_001_[正常系]条件に合致する１件の削除、削除したデータの取得")
    public void findAllAndRemove_001() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                createPerson("1"),
                createPerson("2"),
                createPerson("3")
        );

        // -- 実行 --
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is("1"));

        List<Person> personList = mongoOperations.findAllAndRemove(query, Person.class);

        // -- 検証 --
        log.info(personList.toString());
        assertThat(personList).hasSize(1);

        List<Person> personList2 = mongoOperations.find(new Query(), Person.class);
        log.info(personList2.toString());
        assertThat(personList2).hasSize(2);

    }

    @Test
    @DisplayName("findAllAndRemove_001_[正常系]条件に合致する全件の削除、削除したデータの取得")
    public void findAllAndRemove_002() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                createPerson("1"),
                createPerson("2"),
                createPerson("3")
        );

        // -- 実行 --
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex("name$"));

        List<Person> personList = mongoOperations.findAllAndRemove(query, Person.class);

        // -- 検証 --
        log.info(personList.toString());
        assertThat(personList).hasSize(3);

        List<Person> personList2 = mongoOperations.find(new Query(), Person.class);
        log.info(personList2.toString());
        assertThat(personList2).hasSize(0);

    }

    // -- findDistinct --

    @Test
    @DisplayName("findDistinct_001_[正常系]")
    public void findDistinct_001() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                new Person("1", "same_name", 1),
                new Person("2", "same_name", 1),
                new Person("3", "difference_name", 1)
        );

        // -- 実行 --
        Query query = new Query();

        List<String> nameList = mongoOperations.findDistinct(query, "name", Person.class, String.class);

        // -- 検証 --
        log.info(nameList.toString());
        assertThat(nameList).contains("difference_name", "same_name");

    }

    // -- count --

    @Test
    @DisplayName("count_001_[正常系]")
    public void count_001() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                createPerson("1"),
                createPerson("2"),
                createPerson("3")
        );

        // -- 実行 --
        Query query = new Query();
        Long actual = mongoOperations.count(query, Person.class);

        // -- 検証 --
        assertThat(actual).isEqualTo(3);

    }

    // -- exist --

    @Test
    @DisplayName("exist_001_[正常系]検索条件に合致するレコードが存在する場合はTrue")
    public void exist_001() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                createPerson("1"),
                createPerson("2"),
                createPerson("3")
        );

        // -- 実行 --
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is("1"));
        boolean actual = mongoOperations.exists(query, Person.class);

        // -- 検証 --
        assertThat(actual).isTrue();

    }

    @Test
    @DisplayName("exist_002_[正常系]検索条件に合致するレコードが存在しなければ場合はFalse")
    public void exist_002() {

        // -- 準備 --
        mongoOperations.dropCollection("person");
        insertIntoDatabase(
                createPerson("1"),
                createPerson("2"),
                createPerson("3")
        );

        // -- 実行 --
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is("not exists"));
        boolean actual = mongoOperations.exists(query, Person.class);

        // -- 検証 --
        assertThat(actual).isFalse();

    }


    // -- その他のメソッドはテスト省略 --
    // @see https://docs.spring.io/spring-data/mongodb/docs/current/api/org/springframework/data/mongodb/core/MongoOperations.html
    /*
        bulkOps
        indexOps

        aggregate
        aggregateStream
        execute
        executeCommand
        executeQuery
        geoNear
        getConverter
        mapReduce
        stream
        withSession

        getCollection
        getCollectionName

        collectionExists
        createCollection
        dropCollection
     */

}
