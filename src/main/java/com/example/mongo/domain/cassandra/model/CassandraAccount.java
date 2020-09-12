package com.example.mongo.domain.cassandra.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.SASI;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(value = "account")
public class CassandraAccount {

    /**
     * バージョン
     */
    @Version
    private Long version;

    /**
     * 作成者
     */
    @CreatedBy
    private String CreatedBy;

    /**
     * 最終更新者
     */
    @LastModifiedBy
    private String LastModifiedBy;

    /**
     * 作成日時
     */
    @CreatedDate
    private LocalDateTime createdDate;

    /**
     * 最終更新日時
     */
    @LastModifiedDate
    private LocalDateTime LastModifiedDate;
    /**
     * ユーザID
     */
    @PrimaryKey
    private String username;
    /**
     * パスワード
     */
    private String password;
    /**
     * 名
     */
    @Indexed
    private String firstName;
    /**
     * 姓
     */
    @Indexed
    private String lastName;
    /**
     * メールアドレス
     */
    private String email;
    /**
     * URL
     */
    private String url;
    /**
     * プロフィール
     */
    private String profile;
    /**
     * ロール
     */
    private List<String> roles;

}
