package com.itrach.cbinterceptor.aop;

import com.itrach.cbinterceptor.config.Config;
import com.itrach.cbinterceptor.exception.BadRequestException;
import com.itrach.cbinterceptor.model.CBResponse;
import com.itrach.cbinterceptor.service.MethodService;
import com.itrach.cbinterceptor.service.UserService;
import org.apache.commons.lang3.Validate;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@Aspect
public class CbInterceptorAspect {

    private final Config config;
    private final UserService userService;
    private final MethodService methodService;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public CbInterceptorAspect(Config config, UserService userService, MethodService methodService) {
        this.config = config;
        this.userService = userService;
        this.methodService = methodService;
    }

    @Pointcut("@annotation(com.itrach.cbinterceptor.annotation.CbInterceptor)")
    public void cbInterceptorAnnotationPointcut() {
    }

    @Around("cbInterceptorAnnotationPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = null;
        for (Object o : args) {
            if (o instanceof HttpServletRequest) {
                request = (HttpServletRequest) o;
            }
        }
        Validate.notNull(request, "request parameter is mandatory: %s", joinPoint);

        if (methodService.isMethodFaulty(request)) {
            return new CBResponse(20, "This method is unavailable. End.");
        }
        if (userService.isMethodFaulty(request)) {
            return new CBResponse(21, "This method is unavailable for you.");
        }
        try {
            userService.processUserMeta(request);
        } catch (BadRequestException e) {
            return new CBResponse(22, "Request blocked for this user: " + e.getMessage());
        }

        try {
            return executorService.submit(() -> {
                try {
                    return joinPoint.proceed();
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }).get(config.getMethodTimeoutSeconds(), TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            handleMethodError(request);
            return new CBResponse(23, "Method timeout." + e.getMessage());
        } catch (Throwable e) {
            handleMethodError(request);
            throw e;
        }
    }

    private void handleMethodError(HttpServletRequest request) {
        methodService.handleMethodException(request);
        userService.handleMethodException(request);
    }

}
