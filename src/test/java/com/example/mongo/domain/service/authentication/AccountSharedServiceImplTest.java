package com.example.mongo.domain.service.authentication;

import com.example.mongo.domain.model.authentication.*;
import com.example.mongo.domain.model.common.TempFile;
import com.example.mongo.domain.repository.authentication.*;
import com.example.mongo.domain.repository.common.TempFileRepository;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
class AccountSharedServiceImplTest {

    @Autowired
    AccountSharedService target;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountImageRepository accountImageRepository;

    @Autowired
    TempFileRepository tempFileRepository;

    @Autowired
    SuccessfulAuthenticationRepository successfulAuthenticationRepository;

    @Autowired
    FailedAuthenticationRepository failedAuthenticationRepository;

    @Autowired
    PasswordHistoryRepository passwordHistoryRepository;

    // ---- ヘルパー関数 ----
    private Account createAccount(String username) {
        return Account.builder()
                .roles(Lists.newArrayList("USER"))
                .username(username)
                .password("Password:" + username)
                .firstName("FirstName:" + username)
                .lastName("LastName:" + username)
                .email("Email:" + username)
                .url("Url:" + username)
                .profile("Profile:" + username)
                .build();
    }

    private AccountImage createAccountImage(String username) {
        return AccountImage.builder()
                .username(username)
                .extension("png")
                .body(null)
                .build();
    }

    private void insertIntoAccount(Account... accountList) {
        for (Account account : accountList) {
            accountRepository.insert(account);
            accountImageRepository.insert(createAccountImage(account.getUsername()));
        }
    }

    private TempFile createTempFile(String id, String originalName, LocalDateTime uploadedDate) {
        return TempFile.builder()
                .id(id)
                .originalName(originalName)
                .uploadedDate(uploadedDate)
                .body(null)
                .build();
    }

    private TempFile createTempFile(String id) {
        return createTempFile(id, "OriginalName.png",
                LocalDateTime.of(2020, 10, 31, 12, 59, 59));
    }

    private void insertIntoTempfile(TempFile... tempfiles) {
        for (TempFile tempFile : tempfiles) {
            tempFileRepository.insert(tempFile);
        }
    }

    private void insertIntoSuccessfulAuthentication(SuccessfulAuthentication... successfulAuthentications) {
        for (SuccessfulAuthentication successfulAuthentication : successfulAuthentications) {
            successfulAuthenticationRepository.insert(successfulAuthentication);
        }
    }

    private SuccessfulAuthentication createSucessfulAuthentication(String username) {
        SuccessfulAuthentication successfulAuthentications = new SuccessfulAuthentication();
        successfulAuthentications.setUsername(username);
        return successfulAuthentications;
    }

    private void insertIntoFailedAuthentication(FailedAuthentication... failedAuthentications) {
        for (FailedAuthentication failedAuthentication : failedAuthentications) {
            failedAuthenticationRepository.insert(failedAuthentication);
        }
    }

    private FailedAuthentication createFailedAuthentication(String username) {
        FailedAuthentication failedAuthentications = new FailedAuthentication();
        failedAuthentications.setUsername(username);
        return failedAuthentications;
    }

    private void insertIntoPasswordHistory(PasswordHistory... passwordHistories) {
        for (PasswordHistory passwordHistory : passwordHistories) {
            passwordHistoryRepository.insert(passwordHistory);
        }
    }

    private PasswordHistory createPasswordHistory(String username) {
        PasswordHistory passwordHistory = new PasswordHistory();
        passwordHistory.setUsername(username);
        passwordHistory.setPassword("PasswordOf" + username);
        return passwordHistory;
    }


    @BeforeEach
    void setUp() {
        accountRepository.deleteAll();
        accountImageRepository.deleteAll();
        tempFileRepository.deleteAll();
        successfulAuthenticationRepository.deleteAll();
        failedAuthenticationRepository.deleteAll();
        passwordHistoryRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
    }

    // ---- テスト ----

    @Test
    @DisplayName("[正常系]新規登録したら所定のテーブルにデータが格納される。")
    void create_001() {
        // ---- 準備 ----
        insertIntoTempfile(createTempFile("00000000-0000-0000-0000-000000000001"));

        // ---- 実行 ----
        String acutualRawPassword = target.create(createAccount("user1"), "00000000-0000-0000-0000-000000000001");

        // ---- 検証 ----
        assertThat(acutualRawPassword).isNotNull();
        assertThat(accountRepository.findById("user1")).isNotNull();
    }

    @Test
    @DisplayName("[異常系]指定したユーザが既に存在する場合は例外")
    void create_002() {
        // ---- 準備 ----
        insertIntoAccount(createAccount("user1"));
        insertIntoTempfile(createTempFile("00000000-0000-0000-0000-000000000001"));

        // ---- 実行 ----
        assertThatThrownBy(() -> {
            target.create(createAccount("user1"), "00000000-0000-0000-0000-000000000001");
        })
                // ---- 検証 ----
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("[正常系]指定されたキーに一致するユーザが存在すればデータを取得する。戻値はアカウントロールエンティティ")
    void findOne_001() {
        // ---- 準備 ----
        insertIntoAccount(createAccount("user1"));

        // ---- 実行 ----
        Account actual = target.findOne("user1");

        // ---- 検証 ----
        assertThat(actual)
                .isNotNull()
                .isEqualToComparingOnlyGivenFields(createAccount("user1"),
                        "username", "password", "firstName", "lastName", "email", "url", "profile", "roles"); // フィールド指定の比較もできる
    }

    @Test
    @DisplayName("[異常系]指定されたキーに一致するユーザが存在しなければ例外")
    void findOne_002() {
        // ---- 準備 ----

        // ---- 実行 ----
        assertThatThrownBy(() -> {
            target.findOne("user1");
        })
                // ---- 検証 ----
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("[正常系]指定されたユーザの最終ログイン日時を取得")
    void getLastLoginDate_001() {
        // ---- 準備 ----
        insertIntoAccount(createAccount("user1"));
        SuccessfulAuthentication event1 = successfulAuthenticationRepository.insert(createSucessfulAuthentication("user1"));
        SuccessfulAuthentication event2 = successfulAuthenticationRepository.insert(createSucessfulAuthentication("user1"));

        // ---- 実行 ----
        LocalDateTime actualLoginDate = target.getLastLoginDate("user1");

        // ---- 検証 ----

        log.info(event1.getAuthenticationTimestamp().toString());
        log.info(event2.getAuthenticationTimestamp().toString());
        assertThat(actualLoginDate).isEqualTo(event2.getAuthenticationTimestamp());
    }

    @Test
    @DisplayName("[異常系]指定されたユーザの認証成功履歴がなければNullを返す")
    void getLastLoginDate_002() {
        // ---- 準備 ----
        insertIntoAccount(createAccount("user1"));

        // ---- 実行 ----
        LocalDateTime actualLoginDate = target.getLastLoginDate("user1");

        // ---- 検証 ----
        assertThat(actualLoginDate).isNull();
    }

    @Test
    @DisplayName("[正常系]指定されたユーザが存在すればTrue")
    void exists_001() {
        // ---- 準備 ----
        insertIntoAccount(createAccount("user1"));

        // ---- 実行 ----
        Boolean actualExists = target.exists("user1");

        // ---- 検証 ----
        assertThat(actualExists).isTrue();
    }

    @Test
    @DisplayName("[正常系]指定されたユーザが存在しなければFalse")
    void exists_002() {
        // ---- 準備 ----

        // ---- 実行 ----
        Boolean actualExists = target.exists("user1");

        // ---- 検証 ----
        assertThat(actualExists).isFalse();
    }


    /**
     * 注意: isLocked()のテスト結果は環境設定に依存します。
     * # ロックアウトの継続時間(秒)
     * security.lockingDurationSeconds=3
     * # ロックアウトするまでの認証失敗回数
     * security.lockingThreshold=3
     */
    @Test
    @DisplayName("[正常系]指定されたユーザが認証失敗回数が3以上でロック(True)")
    void isLocked_001() {

        // ---- 準備 ----
        insertIntoAccount(createAccount("user1"));
        insertIntoFailedAuthentication(createFailedAuthentication("user1")); // 1
        insertIntoFailedAuthentication(createFailedAuthentication("user1")); // 2
        insertIntoFailedAuthentication(createFailedAuthentication("user1")); // 3

        // ---- 実行 ----
        Boolean actualLocked = target.isLocked("user1");

        // ---- 検証 ----
        assertThat(actualLocked).isTrue();
    }

    @Test
    @DisplayName("[正常系]指定されたユーザが認証失敗回数が3未満はでロックしない(False)")
    void isLocked_002() {

        // ---- 準備 ----
        insertIntoAccount(createAccount("user1"));
        insertIntoFailedAuthentication(createFailedAuthentication("user1")); // 1
        insertIntoFailedAuthentication(createFailedAuthentication("user1")); // 2

        // ---- 実行 ----
        Boolean actualLocked = target.isLocked("user1");

        // ---- 検証 ----
        assertThat(actualLocked).isFalse();
    }

    @Test
    @DisplayName("[正常系]古い(3秒経過)した認証失敗は、回数に含まれないので、ロックしない(False)")
    void isLocked_003() {

        // ---- 準備 ----
        insertIntoAccount(createAccount("user1"));
        insertIntoFailedAuthentication(createFailedAuthentication("user1")); // 3(古い)
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        insertIntoFailedAuthentication(createFailedAuthentication("user1")); // 1(古くない)
        insertIntoFailedAuthentication(createFailedAuthentication("user1")); // 2(古くない)

        // ---- 実行 ----
        Boolean actualLocked = target.isLocked("user1");

        // ---- 検証 ----
        assertThat(actualLocked).isFalse();
    }

    @Test
    @DisplayName("[正常系]3秒経過していない認証失敗は、回数に含まれる")
    void isLocked_004() {

        // ---- 準備 ----
        insertIntoAccount(createAccount("user1"));
        insertIntoFailedAuthentication(createFailedAuthentication("user1")); // 1
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        insertIntoFailedAuthentication(createFailedAuthentication("user1")); // 2
        insertIntoFailedAuthentication(createFailedAuthentication("user1")); // 3
        insertIntoFailedAuthentication(createFailedAuthentication("user1")); // 4

        // ---- 実行 ----
        Boolean actualLocked = target.isLocked("user1");

        // ---- 検証 ----
        assertThat(actualLocked).isTrue();
    }

    @Test
    @DisplayName("[正常系]パスワードを変更していない場合はTrue")
    void isInitialPassword_001() {

        // ---- 準備 ----
        insertIntoAccount(createAccount("user1"));

        // ---- 実行 ----
        Boolean actualInitialPassword = target.isInitialPassword("user1");

        // ---- 検証 ----
        assertThat(actualInitialPassword).isTrue();
    }

    @Test
    @DisplayName("[正常系]パスワードを変更したらFalse")
    void isInitialPassword_002() {

        // ---- 準備 ----
        insertIntoAccount(createAccount("user2"));
        insertIntoPasswordHistory(createPasswordHistory("user2"));

        // ---- 実行 ----
        Boolean actualInitialPassword = target.isInitialPassword("user2");

        // ---- 検証 ----
        assertThat(actualInitialPassword).isFalse();
    }

    /**
     * 注意: テスト結果が環境変数(application.properties)に依存します。
     * # パスワードの有効期間(秒)
     * security.passwordLifeTimeSeconds=1
     */
    @Test
    @DisplayName("[正常系]パスワードを変更していない場合はTrue")
    void isCurrentPasswordExpired_001() {

        // ---- 準備 ----
        insertIntoAccount(createAccount("user1"));

        // ---- 実行 ----
        Boolean actualCurrentPasswordExpired = target.isCurrentPasswordExpired("user1");

        // ---- 検証 ----
        assertThat(actualCurrentPasswordExpired).isTrue();
    }

    @Test
    @DisplayName("[正常系]パスワードを変更し、有効期限までの間はFALSE")
    void isCurrentPasswordExpired_002() {

        // ---- 準備 ----
        insertIntoAccount(createAccount("user2"));
        insertIntoPasswordHistory(createPasswordHistory("user2"));

        // ---- 実行 ----
        Boolean actualCurrentPasswordExpired = target.isCurrentPasswordExpired("user2");

        // ---- 検証 ----
        assertThat(actualCurrentPasswordExpired).isFalse();
    }

    @Test
    @DisplayName("[正常系]パスワードを変更し、有効期限を過ぎたらTRUE")
    void isCurrentPasswordExpired_003() {

        // ---- 準備 ----
        insertIntoAccount(createAccount("user3"));
        insertIntoPasswordHistory(createPasswordHistory("user3"));

        // ---- 実行 ----
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        Boolean actualCurrentPasswordExpired = target.isCurrentPasswordExpired("user3");

        // ---- 検証 ----
        assertThat(actualCurrentPasswordExpired).isTrue();
    }


    @Test
    @DisplayName("[正常系]パスワードを変更できること")
    void updatePassword_001() {
        // ---- 準備 ----
        insertIntoAccount(createAccount("user1"));

        List<PasswordHistory> beforePasswordHistories = passwordHistoryRepository.findByUsername("user1");

        // ---- 実行 ----
        Boolean actualupdatePassword = target.updatePassword("user1", "NEWPASSWORD");

        // ---- 検証 ----
        assertThat(actualupdatePassword).isTrue();

        List<PasswordHistory> actualPasswordHistories = passwordHistoryRepository.findByUsername("user1");
        assertThat(actualPasswordHistories).hasSize(beforePasswordHistories.size() + 1);
    }


    @Test
    @DisplayName("[正常系]キャッシュがクリアされる(検証方法不明のための実行できることのみ確認)")
    void clearPasswordValidationCache_001() {
        // ---- 準備 ----

        // ---- 実行 ----
        target.clearPasswordValidationCache("user1");
    }

}