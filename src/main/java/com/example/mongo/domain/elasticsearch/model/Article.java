package com.example.mongo.domain.elasticsearch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.data.elasticsearch.annotations.FieldType.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = "blog", type = "article")
public class Article {

    @Id
    private String id;

    @MultiField(mainField = @Field(type = Text, fielddata = true), otherFields = { @InnerField(suffix = "verbatim", type = Keyword) })
    private String title;

    @Field(type = Nested, includeInParent = true)
    private List<Author> authors;

    @Field(type = Keyword)
    private String[] tags;

    @Field(type = Text, fielddata = true)
    private String username;

    @Field(type = Text, fielddata = true)
    private String password;

    @Field(type = Text, fielddata = true)
    private String firstName;

    @Field(type = Text, fielddata = true)
    private String lastName;

    @Field(type = Text, fielddata = true)
    private String email;

    @Field(type = Text, fielddata = true)
    private String url;

    @Field(type = Text, fielddata = true)
    private String profile;


    public Article(String title) {
        this.title = title;
    }



}
