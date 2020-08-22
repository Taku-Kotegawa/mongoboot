package com.example.mongo.domain.model.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordReissueInfo implements Serializable {
    /**
     * トークン
     */
    @Id
    private String token;

    /**
     * ユーザID
     */
    private String username;

    /**
     * 秘密情報
     */
    private String secret;

    /**
     * 有効期限
     */
    private LocalDateTime expiryDate;
}
