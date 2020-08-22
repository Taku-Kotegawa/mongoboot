package com.example.mongo.domain.repository.common;


import com.example.mongo.domain.model.common.TempFile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;

public interface TempFileRepository extends MongoRepository<TempFile, String> {

    long deleteByUploadedDateLessThan(LocalDateTime deleteTo);
}
