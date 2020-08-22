package com.example.mongo.domain.model.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountImage {

    @Id
    private String username;

    /**
     *   拡張子
     */
    private String extension;

    /**
     *   ファイル本体
     */
    private Binary body;
}
