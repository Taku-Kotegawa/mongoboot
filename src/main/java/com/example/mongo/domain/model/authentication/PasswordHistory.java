package com.example.mongo.domain.model.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordHistory implements Serializable, Persistable<String> {
    /**
     * ユーザID
     */
    private String username;

    /**
     * 利用開始日時
     */
    @CreatedDate
    private LocalDateTime useFrom;

    /**
     * パスワード
     */
    private String password;

    @Override
    public String getId() {
        return username;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
