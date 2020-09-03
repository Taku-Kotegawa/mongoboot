package com.example.mongo.app.example.pdfbox;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("pdfbox")
public class PdfboxController {

    @Autowired
    ResourceLoader resourceLoader;

    @ModelAttribute
    public PdfboxForm setUp() {
        return new PdfboxForm();
    }

    @GetMapping(params = "form")
    public String form(Model model) {
        return "pdfbox/form";
    }

    @PostMapping()
    public String post(@Validated PdfboxForm form,
                       BindingResult bindingResult,
                       Model model) {

        if (bindingResult.hasErrors()) {
            return form(model);
        }

        String queryParam = "?startpage=" + form.getStartPage().toString();
        if (form.getEndPage() != null) {
            queryParam += "&endpage=" + form.getEndPage().toString();
        }

        return "redirect:pdfbox/download" + queryParam;
    }


    @ResponseBody
    @GetMapping(
            value = "download",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public byte[] download(
            Model model,
            @RequestParam(name = "startpage", required = false) Integer startPage,
            @RequestParam(name = "endpage", required = false) Integer endPage) throws IOException {

        Resource resource = resourceLoader.getResource("classpath:pdf/ec2-ug.pdf");

        Splitter splitter = new Splitter();
        PDDocument document = null;
        List<PDDocument> documents = null;
        document = PDDocument.load(resource.getFile());
        int numberOfPages = document.getNumberOfPages();


        if (startPage != null) {
            splitter.setStartPage(Math.min(startPage, numberOfPages));
        }

        splitter.setEndPage(endPage != null ? endPage : numberOfPages);
        splitter.setSplitAtPage(endPage != null ? endPage : numberOfPages);

        documents = splitter.split( document );


        ByteArrayOutputStream out = new ByteArrayOutputStream();
        documents.get(0).save(out);

        return out.toByteArray();
    }

}
