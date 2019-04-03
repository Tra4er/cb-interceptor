package com.itrach.cbinterceptor.service;

import javax.servlet.http.HttpServletRequest;

public interface MethodService {

    boolean isMethodFaulty(HttpServletRequest request);

    void handleMethodException(HttpServletRequest request);
}
