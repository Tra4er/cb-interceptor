package com.itrach.cbinterceptor.utils;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {

    public static String getMethod(HttpServletRequest request) {
        return request.getRequestURI() + "?" + request.getQueryString();
    }
}
