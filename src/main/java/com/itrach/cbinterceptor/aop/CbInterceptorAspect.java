package com.itrach.cbinterceptor.aop;

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

@Component
@Aspect
public class CbInterceptorAspect {

    private final UserService userService;
    private final MethodService methodService;

    public CbInterceptorAspect(UserService userService, MethodService methodService) {
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
        for(Object o : args) {
            if (o instanceof HttpServletRequest) {
                request = (HttpServletRequest) o;
            }
        }
        Validate.notNull(request, "request parameter is mandatory: %s", joinPoint);

        if (methodService.isMethodBlocked(request)){
            return new CBResponse(20, "This method is unavailable. End.");
        }
        if (userService.isMethodBlocked(request)){
            return new CBResponse(21, "This method is unavailable for you.");
        }
        try {
            userService.processUserMeta(request);
        } catch (BadRequestException e) {
            return new CBResponse(22, "Request blocked for this user: " + e.getMessage());
        }

        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            methodService.handleMethodException(request);
            userService.handleMethodException(request);
            throw e;
        }
    }

}
