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
@AllArgsConstructor
@NoArgsConstructor
public class SuccessfulAuthentication implements Serializable, Persistable<String> {
    /**
     * ユーザID
     */
    private String username;

    /**
     * 成功日時
     */
    @CreatedDate
    private LocalDateTime authenticationTimestamp;

    @Override
    public String getId() {
        return username;
    }

    @Override
    public boolean isNew() {
        return true; // 常に登録
    }
}
