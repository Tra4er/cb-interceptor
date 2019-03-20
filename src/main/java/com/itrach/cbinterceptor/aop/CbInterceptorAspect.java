package com.itrach.cbinterceptor.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CbInterceptorAspect {

    @Pointcut("@annotation(com.itrach.cbinterceptor.annotation.CbInterceptor)")
    public void cbInterceptorAnnotationPointcut() {
    }

    @Around("cbInterceptorAnnotationPointcut()")
    public String around(ProceedingJoinPoint pjp) {
        System.err.println(pjp);
        try {
            pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.err.println("AROUND");
        return "new return";
    }
}
