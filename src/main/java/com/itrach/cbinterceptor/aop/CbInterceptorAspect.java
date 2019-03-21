package com.itrach.cbinterceptor.aop;

import com.itrach.cbinterceptor.component.CallMetaStorage;
import org.apache.commons.lang3.Validate;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class CbInterceptorAspect {

    private Map<String, Integer> counter = new HashMap<>();
    private final CallMetaStorage callMetaStorage;

    public CbInterceptorAspect(CallMetaStorage callMetaStorage) {
        this.callMetaStorage = callMetaStorage;
    }

    @Pointcut("@annotation(com.itrach.cbinterceptor.annotation.CbInterceptor)")
    public void cbInterceptorAnnotationPointcut() {
    }

    @Around("cbInterceptorAnnotationPointcut()")
    public String around(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = null;
        for(Object o : args) {
            if (o instanceof HttpServletRequest) {
                request = (HttpServletRequest) o;
            }
        }
        Validate.notNull(request, "request parameter is mandatory: %s", joinPoint);

        processUserData(joinPoint, request);

//        HttpServletRequest a
        if (counter.containsKey(request.getRemoteAddr())) {
            counter.put(request.getRemoteAddr(), counter.get(request.getRemoteAddr()) + 1);
        } else {
            counter.put(request.getRemoteAddr(), 1);
        }
        System.err.println(counter);
        System.err.println(joinPoint);
        try {
            joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.err.println("AROUND");
        return "new return";
    }

    private void processUserData(ProceedingJoinPoint joinPoint, HttpServletRequest request) {
        // зберегти запит і подивитись скільки помилок і кількість запитів за період.
//        callMetaStorage.setUserMetaStorageMap();
    }
    // builder
}
