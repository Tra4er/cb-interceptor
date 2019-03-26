package com.itrach.cbinterceptor.service;

import com.itrach.cbinterceptor.exception.BadRequestException;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    void processUserMeta(HttpServletRequest request) throws BadRequestException;

    boolean isMethodBlocked(HttpServletRequest request);

    void handleMethodException(HttpServletRequest request);
}
