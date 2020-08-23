package com.example.mongo.app.example.transactionToken;

import lombok.Data;

import java.io.Serializable;

@Data
public class TransactionTokenForm implements Serializable {

    /**
     * テキスト001
     */
    private String text001;

    /**
     * テキスト002
     */
    private String text002;

    /**
     * テキスト003
     */
    private String text003;

    /**
     * テキスト004
     */
    private String text004;

    /**
     * 画面番号
     */
    private int screenId;

}
