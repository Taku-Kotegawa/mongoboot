package com.example.mongo.app.common.exception;

import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;

public class DuplicateKeyBusinessException extends BusinessException {
    public DuplicateKeyBusinessException(ResultMessages messages) {
        super(messages);
    }
}
