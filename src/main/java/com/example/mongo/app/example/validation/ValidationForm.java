package com.example.mongo.app.example.validation;

import com.example.mongo.app.common.catalog.FullHalfJIS2;
import lombok.Data;
import org.hibernate.validator.constraints.CodePointLength;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;
import org.terasoluna.gfw.common.codelist.ExistInCodeList;
import org.terasoluna.gfw.common.codepoints.ConsistOf;
import org.terasoluna.gfw.common.codepoints.catalog.JIS_X_0208_Kanji;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
public class ValidationForm implements Serializable {

    List<@ExistInCodeList(codeListId = "CL_ORDERSTATUS") String> textfield998;
    @NotNull
    private String textfield001;
    @NotBlank
    private String textfield002;
    @NotEmpty
    private String[] textfield003;
    @AssertTrue
    private Boolean textfield004;
    @AssertFalse
    private Boolean textfield005;
    @Null
    private Boolean textfield006;
    @Min(2)
    private Integer textfield007;
    @Max(10)
    private Integer textfield008;
    @DecimalMin("1.0")
    private String textfield011;
    @DecimalMax("99.9")
    private String textfield012;
    @Positive
    private Integer textfield013;
    @PositiveOrZero
    private Long textfield014;
    @Negative
    private Float textfield015;
    @NegativeOrZero
    private Double textfield016;
    @Digits(integer = 3, fraction = 2)
    private String textfield017;
    @Future
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate localdate001;
    @FutureOrPresent
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate localdate002;
    @Past
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate localdate003;
    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate localdate004;
    @Email
    private String textfield018;
    @Pattern(regexp = "^a.*")
    private String textfield009;
    @Size(min = 2, max = 4)
    private String textfield019;
    @Size(min = 2)
    private String textfield020;
    @Size(max = 4)
    private String textfield021;
    @SafeHtml()
    private String textfield010;
    @CodePointLength(min = 1, max = 2)
    private String textfield022;
    @UniqueElements
    private List<String> textfield023;
    @ExistInCodeList(codeListId = "CL_ORDERSTATUS")
    private String textfield999;
    @ConsistOf({JIS_X_0208_Kanji.class})
    private String textfield024;

    @ConsistOf(FullHalfJIS2.class)
    private String textfield025;

    @Valid
    private ValidationChildForm childForm001;

}
