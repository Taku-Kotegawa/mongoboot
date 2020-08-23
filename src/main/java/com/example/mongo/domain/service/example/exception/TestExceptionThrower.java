package com.example.mongo.domain.service.example.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.exception.SystemException;
import org.terasoluna.gfw.common.message.ResultMessages;

import java.io.IOException;
import java.io.UncheckedIOException;

@Slf4j
@Service
@Transactional
public class TestExceptionThrower {

    public void throwBusinessException() {
        ResultMessages messages = ResultMessages.error();
        messages.add("e.ad.od.5001", 10);
        throw new BusinessException(messages);
    }

    public void throwSystemException() {
        throw new SystemException("e.ad.od.5001", "システム例外");
    }

    public void throwResourceNotFoundException() {
        ResultMessages messages = ResultMessages.error();
        messages.add("e.ad.od.5001", 20);
        throw new ResourceNotFoundException(messages, new IOException());
    }

    public void throwIllegalArgumentException() {
        throw new IllegalArgumentException("不正なパラメータ");
    }

    public void throwIllegalStateException() {
        throw new IllegalStateException("不正な状態");
    }

    public void throwUnsupportedOperationException() {
        throw new UnsupportedOperationException("サポートされていない操作");
    }

    public void throwUncheckedIOException() {
        throw new UncheckedIOException("IO例外", new IOException());
    }

}
