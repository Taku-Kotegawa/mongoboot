package com.example.mongo.domain.service.authentication;

import com.example.mongo.domain.model.authentication.PasswordHistory;
import com.example.mongo.domain.repository.authentication.PasswordHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class PasswordHistorySharedServiceImpl implements PasswordHistorySharedService {

    @Autowired
    PasswordHistoryRepository passwordHistoryRepository;

    @Override
    public int insert(PasswordHistory history) {
        passwordHistoryRepository.insert(history);
        return 1;
    }

    @Override
    public List<PasswordHistory> findHistoriesByUseFrom(String username, LocalDateTime useFrom) {
//        return passwordHistoryRepository.findByUsernameAndUsefromGreaterThanEqualSortByUsefromDesc(username, useFrom);

      return passwordHistoryRepository.findByUsernameAndUseFromAfter(username, useFrom);
    }

    @Override
    public List<PasswordHistory> findLatest(String username, int limit) {
        PasswordHistory prob = PasswordHistory.builder().username(username).build();
        Pageable pageable = PageRequest.of(0, limit, Sort.Direction.DESC, "useFrom");

        Page<PasswordHistory> page = passwordHistoryRepository.findAll(Example.of(prob), pageable);
        return page.getContent();
    }
}
