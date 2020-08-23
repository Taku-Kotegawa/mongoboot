package com.example.mongo.app.common.exception;

import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;

public class NoChangeBusinessException extends BusinessException {
    public NoChangeBusinessException(ResultMessages messages) {
        super(messages);
    }
}
