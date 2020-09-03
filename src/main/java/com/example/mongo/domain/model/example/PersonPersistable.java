package com.example.mongo.domain.model.example;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonPersistable implements Persistable<String> {

    @Id
    private String id;
    private String name;
    private Integer age;

    @Version
    private Long version;

    @org.springframework.data.annotation.CreatedBy
    private String CreatedBy;

    @org.springframework.data.annotation.LastModifiedBy
    private String LastModifiedBy;

    @CreatedDate
    private LocalDateTime createdDate;

    @org.springframework.data.annotation.LastModifiedDate
    private LocalDateTime LastModifiedDate;

    public PersonPersistable(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean isNew() {

        return createdDate == null;
    }
}
