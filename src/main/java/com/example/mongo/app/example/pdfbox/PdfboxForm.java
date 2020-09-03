package com.example.mongo.app.example.pdfbox;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PdfboxForm {

    private String pdfFileName;

    @NotNull
    private Integer startPage;

    private Integer endPage;

}
