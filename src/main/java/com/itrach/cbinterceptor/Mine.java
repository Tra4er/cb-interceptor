package com.itrach.cbinterceptor;

import com.itrach.cbinterceptor.annotation.CbInterceptor;
import org.springframework.stereotype.Component;

@Component
public class Mine {

    @CbInterceptor("asdewf")
    public String meth() {
        System.err.println("ewpodk");
        return "return";
    }
}
