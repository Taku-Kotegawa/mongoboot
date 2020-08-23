package com.example.mongo.app.common.exception;

import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;

public class PessimisticLockingFailureBusinessException extends BusinessException {
    public PessimisticLockingFailureBusinessException(ResultMessages messages) {
        super(messages);
    }
}
