package com.example.mongo.app.example.guruguru;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ListItem implements Serializable {

    private String value;

    private String label;

}
