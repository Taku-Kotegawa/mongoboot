package com.example.mongo.domain.model.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    private String id;
    private String name;
    private int age;

}
