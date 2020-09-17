package com.example.mongo.domain.elasticsearch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import static org.springframework.data.elasticsearch.annotations.FieldType.Text;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Author {

    @Field(type = Text)
    private String name;

}
