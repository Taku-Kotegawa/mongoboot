/*
 * Copyright(c) 2013 NTT DATA Corporation. Copyright(c) 2013 NTT Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.example.mongo.domain.service.authentication;

import com.example.mongo.domain.model.authentication.*;
import com.example.mongo.domain.model.common.TempFile;
import com.example.mongo.domain.repository.authentication.AccountImageRepository;
import com.example.mongo.domain.repository.authentication.AccountRepository;
import com.example.mongo.domain.service.common.FileUploadSharedService;
import com.github.dozermapper.core.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.passay.CharacterRule;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.message.ResultMessages;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.mongo.app.common.constants.MessageKeys.E_SL_AC_5001;
import static com.example.mongo.app.common.constants.MessageKeys.E_XX_FW_5001;

// https://stackoverflow.com/questions/29606290/authentication-with-spring-security-spring-data-mongodb

@Slf4j
@Service
public class AccountSharedServiceImpl implements AccountSharedService {

    @Autowired
    Mapper beanMapper;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountImageRepository accountImageRepository;

    @Autowired
    FileUploadSharedService fileUploadSharedService;

    @Autowired
    AuthenticationEventSharedService authenticationEventSharedService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    PasswordGenerator passwordGenerator;

    @Autowired
    PasswordHistorySharedService passwordHistorySharedService;

    @Resource(name = "passwordGenerationRules")
    List<CharacterRule> passwordGenerationRules;

    @Value("${security.lockingDurationSeconds}")
    int lockingDurationSeconds;

    @Value("${security.lockingThreshold}")
    int lockingThreshold;

    @Value("${security.passwordLifeTimeSeconds}")
    int passwordLifeTimeSeconds;

    @Override
    public Account findOne(String username) {
        final Account account = accountRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ResultMessages.error().add(E_XX_FW_5001, username)));
        if (account.getRoles() == null) {
            account.setRoles(new ArrayList<>());
        }
        return account;
    }

    @Override
    public LocalDateTime getLastLoginDate(String username) {

        List<SuccessfulAuthentication> events = authenticationEventSharedService
                .findLatestSuccessEvents(username, 1);

        if (!events.isEmpty()) {
            return events.get(0).getAuthenticationTimestamp();
        } else {
            return null;
        }
    }

    @Override
    public String create(Account account, String imageId) {
        if (account == null) {
            throw new IllegalArgumentException("account was null.");
        }

        if (exists(account.getUsername())) {
            throw new BusinessException(ResultMessages.error().add(
                    E_SL_AC_5001));
        }
        String rawPassword = passwordGenerator.generatePassword(10, passwordGenerationRules);
        account.setPassword(passwordEncoder.encode(rawPassword));
        accountRepository.insert(account);

        if (imageId != null) {
            TempFile tempFile = fileUploadSharedService.findTempFile(imageId);
            AccountImage image = AccountImage.builder()
                    .username(account.getUsername())
                    .extension(StringUtils.getFilenameExtension(tempFile.getOriginalName()))
                    .body(tempFile.getBody()).build();
            accountImageRepository.insert(image);
        }

        return rawPassword;
    }

    @Override
    public boolean exists(String username) {
        return accountRepository.existsById(username);
    }

    @Override
    public boolean isLocked(String username) {
        List<FailedAuthentication> failureEvents = authenticationEventSharedService
                .findLatestFailureEvents(username, lockingThreshold);

        if (failureEvents.size() < lockingThreshold) {
            return false;
        }

        return !failureEvents.get(lockingThreshold - 1)
                .getAuthenticationTimestamp()
                .isBefore(LocalDateTime.now().minusSeconds(lockingDurationSeconds));
    }

    @Override
    @Cacheable("isInitialPassword")
    public boolean isInitialPassword(String username) {
        List<PasswordHistory> passwordHistories = passwordHistorySharedService
                .findLatest(username, 1);
        return passwordHistories.isEmpty();
    }

    @Override
    @Cacheable("isCurrentPasswordExpired")
    public boolean isCurrentPasswordExpired(String username) {
        List<PasswordHistory> passwordHistories = passwordHistorySharedService
                .findLatest(username, 1);

        if (passwordHistories.isEmpty()) {
            return true;
        }

        return passwordHistories
                .get(0)
                .getUseFrom()
                .isBefore(LocalDateTime.now().minusSeconds(passwordLifeTimeSeconds));
    }

    @Override
    @CacheEvict(value = {"isInitialPassword", "isCurrentPasswordExpired"}, key = "#username")
    public boolean updatePassword(String username, String rawPassword) {
        String password = passwordEncoder.encode(rawPassword);

        Account account = findOne(username);
        account.setPassword(password);
        account = accountRepository.save(account);

        passwordHistorySharedService.insert(PasswordHistory.builder()
                .username(username)
                .password(password)
                .useFrom(LocalDateTime.now()).build());

        return (account != null);
    }

    @Override
    @CacheEvict(value = {"isInitialPassword", "isCurrentPasswordExpired"}, key = "#username")
    public void clearPasswordValidationCache(String username) {
    }

    @Override
    public AccountImage getImage(String username) {
        return accountImageRepository.findById(username).orElse(null);
    }

}
