package com.example.mongo.domain.model.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReceivedMail implements Serializable {
    /**
     * 送信者アドレス
     */
    private String from;
    /**
     * 宛先アドレス
     */
    private String to;
    /**
     * 件名
     */
    private String subject;

    /**
     * 本文
     */
    private String text;
}
