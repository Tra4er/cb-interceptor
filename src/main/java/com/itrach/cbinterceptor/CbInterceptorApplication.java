package com.itrach.cbinterceptor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CbInterceptorApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext a = SpringApplication.run(CbInterceptorApplication.class, args);
//        Mine mine = a.getBean(Mine.class);
//        System.err.println(mine.meth());
    }

}
