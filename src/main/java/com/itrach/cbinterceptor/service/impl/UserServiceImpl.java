package com.itrach.cbinterceptor.service.impl;

import com.itrach.cbinterceptor.component.CallMetaStorage;
import com.itrach.cbinterceptor.exception.BadRequestException;
import com.itrach.cbinterceptor.model.user.UserMetaStorage;
import com.itrach.cbinterceptor.service.UserService;
import com.itrach.cbinterceptor.utils.RequestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final CallMetaStorage callMetaStorage;

    @Override
    public void processUserMeta(HttpServletRequest request) throws BadRequestException {
        String userIp = request.getRemoteAddr();
        UserMetaStorage userMetaStorage = callMetaStorage.getUserByIp(userIp);

        if (userMetaStorage == null) {
            // if it's first user request
            userMetaStorage = new UserMetaStorage(userIp);
            userMetaStorage.processUserCall(RequestUtils.getMethod(request));
            callMetaStorage.saveUser(userMetaStorage);
        } else {
            userMetaStorage.checkPermittedUserCalls();
            userMetaStorage.processUserCall(RequestUtils.getMethod(request));
            System.err.println(userMetaStorage);
//        callMetaStorage
        }

    }
}
