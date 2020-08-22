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

import com.example.mongo.domain.model.authentication.Account;

import java.time.LocalDateTime;

public interface AccountSharedService {

    /**
     * キーでアカウントエンティティを取得する。
     *
     * @param username ユーザID
     * @return アカウント・ロールエンティティ
     */
    Account findOne(String username);

    /**
     * @param username
     * @return
     */
    LocalDateTime getLastLoginDate(String username);

    /**
     * 新規アカウントを登録する。(事前にアカウント画像の登録が必要)
     * 初期パスワードを設定し、返す。
     *
     * @param account アカウント・ロールエンティティ
     * @param imageId 画像ファイルを示す一時ファイルの内部番号
     * @return 初期パスワード
     */
    String create(Account account, String imageId);

    /**
     * @param username
     * @return
     */
    boolean exists(String username);

    /**
     * @param username
     * @return
     */
    boolean isLocked(String username);

    /**
     * @param username
     * @return
     */
    boolean isInitialPassword(String username);

    /**
     * @param username
     * @return
     */
    boolean isCurrentPasswordExpired(String username);

    /**
     * @param username
     * @param rawPassword
     * @return
     */
    boolean updatePassword(String username, String rawPassword);

    /**
     * @param username
     */
    void clearPasswordValidationCache(String username);
}
