package com.itrach.cbinterceptor.service.impl;

import com.itrach.cbinterceptor.component.CallMetaStorage;
import com.itrach.cbinterceptor.model.method.MethodMetaStorage;
import com.itrach.cbinterceptor.service.MethodService;
import com.itrach.cbinterceptor.utils.RequestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class MethodServiceImpl implements MethodService {

    public static final int MAX_ERRORS = 2;

    private final CallMetaStorage callMetaStorage;

    public boolean isErrorsExcited(HttpServletRequest request) {
        MethodMetaStorage methodMetaStorage = callMetaStorage.getMethodMetaStorageMap().get(RequestUtils.getMethod(request));
//        boolean result
        // check how long ago
        return methodMetaStorage == null || methodMetaStorage.getErrorsCount() < MAX_ERRORS;
    }

    public void addExceptionForMethod(HttpServletRequest request) {
        MethodMetaStorage methodMetaStorage = callMetaStorage.getMethodMetaStorageMap().get(RequestUtils.getMethod(request));
        methodMetaStorage.setErrorsCount(methodMetaStorage.getErrorsCount() + 1);
    }

}
