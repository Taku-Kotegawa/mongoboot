package com.example.mongo.domain.model.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TempFile implements Serializable, Persistable<String> {
    /**
     * ID(内部番号)
     */
    @Id
    private String id;

    /**
     * オリジナルファイル名
     */
    private String originalName;

    /**
     * アップロード日時
     */
    @CreatedDate
    private LocalDateTime uploadedDate;

    /**
     * ファイル本体
     */
    private Binary body;

    @Override
    public boolean isNew() {
        return true;
    }
}
