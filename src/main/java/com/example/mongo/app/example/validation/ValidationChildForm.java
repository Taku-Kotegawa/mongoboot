package com.example.mongo.app.example.validation;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ValidationChildForm {

    @NotNull
    private String textfield0001;

}
