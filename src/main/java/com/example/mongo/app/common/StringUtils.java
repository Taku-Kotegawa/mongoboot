package com.example.mongo.app.common;

import com.google.common.base.CaseFormat;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * キャメルケースをスネークケース(小文字)に変換
     *
     * @param camelCase キャメルケース文字列
     * @return スネークケース(小文字)文字列
     */
    public static String toLowerSnakeCase(String camelCase) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, camelCase);
    }

    /**
     * キャメルケースをスネークケース(大文字)に変換
     *
     * @param camelCase キャメルケース文字列
     * @return スネークケース(大文字)文字列
     */
    public static String toUpperSnakeCase(String camelCase) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, camelCase);
    }

    /**
     * スネークケースをキャメルケース(小文字)に変換
     *
     * @param snakeCase 　スネークケース文字列
     * @return キャメルケース(小文字)文字列
     */
    public static String toLowerCamelCase(String snakeCase) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, snakeCase);
    }

    /**
     * スネークケースをキャメルケース(小文字)に変換
     *
     * @param snakeCase 　スネークケース文字列
     * @return キャメルケース(大文字)文字列
     */
    public static String toUpperCamelCase(String snakeCase) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, snakeCase);
    }


}
