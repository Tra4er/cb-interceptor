package com.itrach.cbinterceptor.controller;

import com.hazelcast.core.HazelcastInstance;
import com.itrach.cbinterceptor.annotation.CbInterceptor;
import com.itrach.cbinterceptor.annotation.Request;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class MainController {

    @GetMapping
    @CbInterceptor("ksjdfh")
    public String get(HttpServletRequest request) {
        System.err.println("HERE " + request.getRemoteAddr());
        return "Response from Get";
    }

}
