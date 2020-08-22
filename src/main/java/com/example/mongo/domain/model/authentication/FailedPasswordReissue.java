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
public class FailedPasswordReissue implements Serializable {
    /**
     * トークン
     */
    @Id
    private String token;

    /**
     * 試行日時
     */
    private LocalDateTime attemptDate;
}
