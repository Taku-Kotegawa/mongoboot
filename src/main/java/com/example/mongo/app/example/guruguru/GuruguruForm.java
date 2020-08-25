package com.example.mongo.app.example.guruguru;


import com.example.mongo.app.common.validation.NotContainControlChars;
import com.example.mongo.app.common.validation.UploadFileMaxSize;
import com.example.mongo.app.common.validation.UploadFileNotEmpty;
import com.example.mongo.app.common.validation.UploadFileRequired;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class GuruguruForm {

    /**
     * テキストフィールド001
     */
    @NotNull
    @NotContainControlChars
    private String textfield001;

    /**
     * テキストフィールド002(数値・整数)
     */
    @NotNull
    private Integer textfield002;

    /**
     * テキストフィールド002(数値・小数あり)
     */
    @NotNull
    private Float textfield003;

    /**
     * テキストフィールド(真偽値)
     */
    @NotNull
    private Boolean textfield004;

    /**
     * テキストフィールド(文字列・複数の値)
     */
    @NotNull
    @NotEmpty
    private List<String> textfield005;

    /**
     * ラジオボタン
     */
    @NotNull
    private Boolean radio001;

    /**
     * ラジオボタン2
     */
    @NotNull
    private String radio002;

    /**
     * チェックボックス1
     */
    @NotNull
    private String checkbox001;

    /**
     * チェックボックス2
     */
    @NotNull
    @NotEmpty
    private List<String> checkbox002;

    /**
     * テキストエリア1
     */
    @NotNull
    private String textarea001;

    /**
     * 日付
     */
    @NotNull
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate localdate001;

    /**
     * 日付時刻
     */
    @NotNull
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime localdatetime001;

    /**
     * セレクト(単一の値)
     */
    @NotNull
    private String select001;

    /**
     * セレクト(単一の値)
     */
    @NotNull
    private String select005;

    /**
     * セレクト(CodeList)
     */
    @NotNull
    private String orderStatus;

    /**
     * セレクト(複数の値)
     */
    @NotNull
    @NotEmpty
    private List<String> select002;

    /**
     * セレクト(Select2 単一の値)
     */
    @NotNull
    private String select003;

    /**
     * セレクト(Select2 複数の値)
     */
    @NotNull
    @NotEmpty
    private List<String> select004;

    /**
     * コンボボックス1
     */
    @NotNull
    private String combobox001;

    /**
     * コンボボックス2(複数の値)
     */
    @NotNull
    @NotEmpty
    private List<String> combobox002;

    /**
     * コンボボックス3
     */
    @NotNull
    private String combobox003;

    /**
     * コンボボックス4
     */
    @NotNull
    private String combobox004;

    /**
     * コンボボックス5
     */
    @NotNull
    private String combobox005;


    /**
     * ファイル
     */
    @UploadFileRequired
    @UploadFileNotEmpty
    @UploadFileMaxSize(value = 2024 * 1024)
    private transient MultipartFile file001;

}
