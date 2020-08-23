package com.example.mongo.app.example.authentication;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationTestForm {

    private String test001;
    private Integer test002;
    private String[] test011;
    private List<String> test101;
}
