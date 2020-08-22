package com.example.mongo.domain.model.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Account implements Serializable, Persistable<String> {

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
    @Id
    private String username;
    /**
     * パスワード
     */
    private String password;
    /**
     * 名
     */
    private String firstName;
    /**
     * 姓
     */
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

    @Override
    public String getId() {
        return this.username;
    }

    @Override
    public boolean isNew() {
        return this.createdDate == null;
    }


}
