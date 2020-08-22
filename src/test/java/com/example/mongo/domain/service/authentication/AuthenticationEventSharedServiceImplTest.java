package com.example.mongo.domain.service.authentication;

import com.example.mongo.domain.model.authentication.FailedAuthentication;
import com.example.mongo.domain.model.authentication.SuccessfulAuthentication;
import com.example.mongo.domain.repository.authentication.FailedAuthenticationRepository;
import com.example.mongo.domain.repository.authentication.SuccessfulAuthenticationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AuthenticationEventSharedServiceImplTest {

    @Autowired
    AuthenticationEventSharedService target;

    @Autowired
    SuccessfulAuthenticationRepository successfulAuthenticationRepository;

    @Autowired
    FailedAuthenticationRepository failedAuthenticationRepository;

    @BeforeEach
    void setUp() {
        successfulAuthenticationRepository.deleteAll();
        failedAuthenticationRepository.deleteAll();
    }

    // -- authenticationSuccess(String username) --
    @Test
    @DisplayName("authenticationSuccess_001_登録できる")
    public void authenticationSuccess_test001() {
        // ---- 準備 ----
        LocalDateTime beforeExecuteTime = LocalDateTime.now();
        // ---- 実行 ----
        target.authenticationSuccess("username001");
        LocalDateTime afterExecuteTime = LocalDateTime.now();

        // ---- 検証 ----
        List<SuccessfulAuthentication> actual = successfulAuthenticationRepository.findAll();
        assertThat(actual).hasSize(1); // 件数は1件
        assertThat(actual.get(0).getUsername()).isEqualTo("username001");
        assertThat(actual.get(0).getAuthenticationTimestamp())
                .isNotNull()
                .isAfterOrEqualTo(beforeExecuteTime)
                .isBeforeOrEqualTo(afterExecuteTime);
    }

    private SuccessfulAuthentication createSuccess(String username, LocalDateTime time) {
        return SuccessfulAuthentication.builder()
                .username(username)
                .authenticationTimestamp(time)
                .build();
    }

    private void insertIntoSuccess(SuccessfulAuthentication... record) {
        successfulAuthenticationRepository.insert(Arrays.asList(record));
    }

    private FailedAuthentication createFail(String username, LocalDateTime time) {
        return FailedAuthentication.builder().username(username).authenticationTimestamp(time).build();
    }

    private void insertIntoFailure(FailedAuthentication... record) {
        failedAuthenticationRepository.insert(Arrays.asList(record));
    }


    // -- findLatestSuccessEvents(String username, int count) --
    @Test
    @DisplayName("findLatestSuccessEvents_001_新しい方から指定した件数取得できる")
    public void findLatestSuccessEvents_test001() {
        // ---- 準備 ----
        insertIntoSuccess(
                createSuccess("1", LocalDateTime.of(2020, 1, 1, 12, 34, 1)),
                createSuccess("1", LocalDateTime.of(2020, 1, 1, 12, 34, 2)),
                createSuccess("1", LocalDateTime.of(2020, 1, 1, 12, 34, 3)),
                createSuccess("2", LocalDateTime.of(2020, 1, 1, 12, 34, 1)),
                createSuccess("2", LocalDateTime.of(2020, 1, 1, 12, 34, 2)),
                createSuccess("2", LocalDateTime.of(2020, 1, 1, 12, 34, 3))
        );

        // ---- 実行 ----
        List<SuccessfulAuthentication> actual = target.findLatestSuccessEvents("1", 2);

        // ---- 検証 ----
        assertThat(actual).hasSize(2);
        assertThat(actual.get(0).getAuthenticationTimestamp()).isEqualTo(LocalDateTime.of(2020, 1, 1, 12, 34, 3));
    }

    @Test
    @DisplayName("findLatestSuccessEvents_002_Failureイベントが削除される")
    public void findLatestSuccessEvents_test002() {
        // ---- 準備 ----
        insertIntoFailure(
                createFail("1", LocalDateTime.of(2020, 1, 1, 12, 34, 1))
        );

        // ---- 実行 ----
        target.authenticationSuccess("1");
        List<FailedAuthentication> actual = failedAuthenticationRepository.findAll();

        // ---- 検証 ----
        assertThat(actual).hasSize(0);

    }

    // -- authenticationFailure(String username) --
    @Test
    @DisplayName("authenticationFailure_001_登録できる")
    public void authenticationFailure_test001() {
        // ---- 準備 ----
        LocalDateTime beforeExecuteTime = LocalDateTime.now();
        // ---- 実行 ----
        target.authenticationFailure("username001");
        LocalDateTime afterExecuteTime = LocalDateTime.now();

        // ---- 検証 ----
        List<FailedAuthentication> actual = failedAuthenticationRepository.findAll();
        assertThat(actual).hasSize(1); // 件数は1件
        assertThat(actual.get(0).getUsername()).isEqualTo("username001");
        assertThat(actual.get(0).getAuthenticationTimestamp())
                .isNotNull()
                .isAfterOrEqualTo(beforeExecuteTime)
                .isBeforeOrEqualTo(afterExecuteTime);
    }

    // -- findLatestFailureEvents(String username, int count) --
    @Test
    @DisplayName("findLatestFailureEvents_001_新しい方から指定した件数取得できる")
    public void findLatestFailureEvents_test001() {
        // ---- 準備 ----
        insertIntoFailure(
                createFail("1", LocalDateTime.of(2020, 1, 1, 12, 34, 1)),
                createFail("1", LocalDateTime.of(2020, 1, 1, 12, 34, 2)),
                createFail("1", LocalDateTime.of(2020, 1, 1, 12, 34, 3)),
                createFail("2", LocalDateTime.of(2020, 1, 1, 12, 34, 1)),
                createFail("2", LocalDateTime.of(2020, 1, 1, 12, 34, 2)),
                createFail("2", LocalDateTime.of(2020, 1, 1, 12, 34, 3))
        );

        // ---- 実行 ----
        List<FailedAuthentication> actual = target.findLatestFailureEvents("1", 2);

        // ---- 検証 ----
        assertThat(actual).hasSize(2);
        assertThat(actual.get(0).getAuthenticationTimestamp()).isEqualTo(LocalDateTime.of(2020, 1, 1, 12, 34, 3));
    }

    // -- deleteFailureEventByUsername(String username) --
    @Test
    @DisplayName("deleteFailureEventByUsername_001_まとめて削除できる")
    public void deleteFailureEventByUsername_test001() {
        // ---- 準備 ----
        insertIntoFailure(
                createFail("1", LocalDateTime.of(2020, 1, 1, 12, 34, 1)),
                createFail("1", LocalDateTime.of(2020, 1, 1, 12, 34, 2)),
                createFail("1", LocalDateTime.of(2020, 1, 1, 12, 34, 3)),
                createFail("2", LocalDateTime.of(2020, 1, 1, 12, 34, 1)),
                createFail("2", LocalDateTime.of(2020, 1, 1, 12, 34, 2)),
                createFail("2", LocalDateTime.of(2020, 1, 1, 12, 34, 3))
        );
        // ---- 実行 ----
        long actual = target.deleteFailureEventByUsername("1");

        // ---- 検証 ----
        assertThat(actual).isEqualTo(3);
        assertThat(failedAuthenticationRepository.count()).isEqualTo(3); // 残りは3件
    }

}