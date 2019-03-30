package com.itrach.cbinterceptor.service.impl;

import com.itrach.cbinterceptor.config.Config;
import com.itrach.cbinterceptor.repository.MethodMetaRepository;
import com.itrach.cbinterceptor.service.MethodService;
import com.itrach.cbinterceptor.utils.RequestUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MethodServiceImpl implements MethodService {

    private final Config config;
    private final MethodMetaRepository callMetaRepository;

    public boolean isMethodBlocked(HttpServletRequest request) {
        CircularFifoQueue<LocalDateTime> methodError =
                callMetaRepository.getMethodMetaByName(RequestUtils.getMethod(request));
        if (methodError == null) {
            return false;
        }
        LocalDateTime lastError = methodError.get(methodError.size() - 1);
        if (lastError.plusSeconds(config.getMethodErrorCooldownSeconds()).isBefore(LocalDateTime.now())) {
            return false;
        }
        return methodError.isAtFullCapacity();
    }

    // перевіряти чи сталася помилка методу для кількох користувачів.
    // Якщо помилка тільки у одного то блокувати тільки того користувача. Якщо у багатьох то блокуємо метод для всіх.
    public void handleMethodException(HttpServletRequest request) {
        System.err.println("HERE exceptions");
        CircularFifoQueue<LocalDateTime> methodError =
                callMetaRepository.getMethodMetaByName(RequestUtils.getMethod(request));
        if (methodError == null) {
            methodError = new CircularFifoQueue<>(config.getMethodErrorsCapacity());
            methodError.add(LocalDateTime.now());
            callMetaRepository.save(RequestUtils.getMethod(request), methodError);
        }
        methodError.add(LocalDateTime.now());
    }

}
