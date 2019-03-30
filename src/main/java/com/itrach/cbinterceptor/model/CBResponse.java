package com.itrach.cbinterceptor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CBResponse {

    private int code;
    private String message;

}
