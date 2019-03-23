package com.itrach.cbinterceptor.model.user;

import com.itrach.cbinterceptor.exception.BadRequestException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class UserMetaStorage {

    public static final int MAX_REQUESTS_PER_METHOD = 3;
    public static final int MAX_REQUESTS_TOTAL = 5;
    public static final int INTERVAL_SEC = 20;
    public static final int COOLDOWN_SEC = 30;

    private String ip;
    private boolean isLocked;
    private LocalDateTime lockTime;
    private boolean isSlowDown;
    // ключем є назва методу із конкретними параметрами. якщо метод валиться то блокуємо метод тільки з цими параметрами
    private Map<String, CircularFifoQueue<Integer>> errorCalls;
    private Map<String, CircularFifoQueue<LocalDateTime>> casualCalls = new HashMap<>();
    // назва методу, кількість запитів за період, кількість помилок.

    public UserMetaStorage(String ip) {
        this.ip = ip;
    }

    public void checkPermittedUserCalls() throws BadRequestException {
        if (lockTime != null && lockTime.plusSeconds(COOLDOWN_SEC).isBefore(LocalDateTime.now())) {
            return;
        }
        int result = 0;
        for(String key : casualCalls.keySet()) {
            result += casualCalls.get(key).size();
            if (result > MAX_REQUESTS_TOTAL) {
                lockTime = LocalDateTime.now();
                throw new BadRequestException("Too much calls into the system.");
            }
        }
    }

    public void processUserCall(String method) throws BadRequestException {

        CircularFifoQueue<LocalDateTime> callsTime = casualCalls.get(method);
        if (callsTime == null) {
            callsTime = new CircularFifoQueue<>(MAX_REQUESTS_PER_METHOD);
            callsTime.add(LocalDateTime.now());
            casualCalls.put(method, callsTime);
        } else {
            boolean isThereOldRequests = true;
            for (LocalDateTime t : callsTime) {
                if (LocalDateTime.now().minusSeconds(INTERVAL_SEC).isBefore(t)) {
                    isThereOldRequests = false;
                } else {
                    callsTime.remove(t);
                }
            }
            if (!isThereOldRequests && callsTime.size() == MAX_REQUESTS_PER_METHOD) {
                throw new BadRequestException("Too much calls into this method.");
            }

            callsTime.add(LocalDateTime.now());
        }

//        CircularFifoQueue<Integer> userCallMethodMetadata = userCalls.get(method);
//        userCallMethodMetadata.
    }
}
