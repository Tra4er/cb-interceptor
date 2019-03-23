package com.itrach.cbinterceptor.service;

import javax.servlet.http.HttpServletRequest;

public interface MethodService {

    boolean isErrorsExcited(HttpServletRequest request);
    void addExceptionForMethod(HttpServletRequest request);
}
