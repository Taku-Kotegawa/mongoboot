package com.example.mongo.domain.cassandra.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(value = "test_table")
public class TestJpaEntity {

    @PrimaryKey("id")
    private String table_id;

    private String data;

    @Column("update_time")
    private Date updateTime;

}