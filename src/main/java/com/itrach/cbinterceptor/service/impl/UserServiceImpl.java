package com.itrach.cbinterceptor.service.impl;

import com.itrach.cbinterceptor.config.Config;
import com.itrach.cbinterceptor.exception.BadRequestException;
import com.itrach.cbinterceptor.model.UserMetadata;
import com.itrach.cbinterceptor.repository.UserMetaRepository;
import com.itrach.cbinterceptor.service.UserService;
import com.itrach.cbinterceptor.utils.RequestUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Config config;
    private final UserMetaRepository userMetaRepository;

    @Override
    public void processUserMeta(HttpServletRequest request) throws BadRequestException {
        String userIp = request.getRemoteAddr();
        UserMetadata userMetadata = userMetaRepository.getUserByIp(userIp);

        if (userMetadata == null) {
            // if it's first user request
            userMetadata = new UserMetadata();
            userMetadata.setIp(userIp);
            processUserCall(RequestUtils.getMethod(request), userMetadata);
            userMetaRepository.saveUser(userMetadata);
        } else {
            checkPermittedUserCalls(userMetadata);
            processUserCall(RequestUtils.getMethod(request), userMetadata);
        }
    }

    @Override
    public boolean isMethodFaulty(HttpServletRequest request) {
        UserMetadata userMetadata = userMetaRepository.getUserByIp(request.getRemoteAddr());
        if (userMetadata == null) {
            return false;
        }
        return isMethodFaulty(RequestUtils.getMethod(request), userMetadata);
    }

    @Override
    public void handleMethodException(HttpServletRequest request) {
        UserMetadata userMetadata = userMetaRepository.getUserByIp(request.getRemoteAddr());
        if (userMetadata == null) {
            userMetadata = new UserMetadata();
            addExceptionForMethod(RequestUtils.getMethod(request), userMetadata);
            userMetaRepository.saveUser(userMetadata);
        }
        addExceptionForMethod(RequestUtils.getMethod(request), userMetadata);
    }

    public void checkPermittedUserCalls(UserMetadata userMetadata) throws BadRequestException {
        if (userMetadata.getLockTime() != null
                && userMetadata.getLockTime().plusSeconds(config.getUserCooldownSeconds())
                .isBefore(LocalDateTime.now())) {
            return;
        }
        int result = 0;
        for (String key : userMetadata.getCasualCalls().keySet()) {
            result += userMetadata.getCasualCalls().get(key).size();
            if (result > config.getUserRequestsLimitTotal()) {
                userMetadata.setLockTime(LocalDateTime.now());
                throw new BadRequestException("Too much calls into the system.");
            }
        }
    }

    public void processUserCall(String method, UserMetadata userMetadata) throws BadRequestException {
        CircularFifoQueue<LocalDateTime> callsTime = userMetadata.getCasualCalls().get(method);
        if (callsTime == null) {
            callsTime = new CircularFifoQueue<>(config.getUserRequestsLimitPerMethod());
            callsTime.add(LocalDateTime.now());
            userMetadata.getCasualCalls().put(method, callsTime);
        } else {
            boolean isThereOldRequests = true;
            for (LocalDateTime t : callsTime) {
                if (LocalDateTime.now().minusSeconds(config.getUserSecondsPerRequestsLimit()).isBefore(t)) {
                    isThereOldRequests = false;
                } else {
                    callsTime.remove(t);
                }
            }
            if (!isThereOldRequests && callsTime.size() == config.getUserRequestsLimitPerMethod()) {
                throw new BadRequestException("Too much calls into this method.");
            }

            callsTime.add(LocalDateTime.now());
        }

    }

    public boolean isMethodFaulty(String method, UserMetadata userMetadata) {
        CircularFifoQueue<LocalDateTime> errorMethod = userMetadata.getErrorCalls().get(method);
        if (errorMethod == null) {
            return false;
        }
        LocalDateTime lastError = errorMethod.get(errorMethod.size() - 1);
        if (lastError.plusSeconds(config.getUserErrorCooldownSeconds()).isBefore(LocalDateTime.now())) {
            return false;
        }
        return errorMethod.isAtFullCapacity();
    }

    public void addExceptionForMethod(String method, UserMetadata userMetadata) {
        CircularFifoQueue<LocalDateTime> methodError = userMetadata.getErrorCalls().get(method);
        if (methodError == null) {
            methodError = new CircularFifoQueue<>(config.getUserErrorsCapacity());
            methodError.add(LocalDateTime.now());
            userMetadata.getErrorCalls().put(method, methodError);
        } else {
            methodError.add(LocalDateTime.now());
        }
    }
}
