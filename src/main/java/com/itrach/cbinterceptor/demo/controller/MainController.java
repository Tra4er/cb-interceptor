package com.itrach.cbinterceptor.demo.controller;

import com.itrach.cbinterceptor.annotation.CbInterceptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class MainController {

    @GetMapping
    @CbInterceptor
    public Object get(HttpServletRequest request) {
        return "Response from Get";
    }

    @GetMapping("/exception")
    @CbInterceptor
    public Object exception(HttpServletRequest request) throws Exception {
        throw new Exception("Internal TEST Exception");
    }

}
