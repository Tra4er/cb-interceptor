package com.itrach.cbinterceptor.model;

import com.itrach.cbinterceptor.config.Config;
import com.itrach.cbinterceptor.exception.BadRequestException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
public class UserMetadata {

    private String ip;
    private boolean isLocked;
    private LocalDateTime lockTime;
    private boolean isSlowDown;
    // ключем є назва методу із конкретними параметрами. якщо метод валиться то блокуємо метод тільки з цими параметрами
    private Map<String, CircularFifoQueue<LocalDateTime>> errorCalls = new HashMap<>(); // тайм щоб дивитись коли був останній виклик і дати можливість спробувати ще раз
    private Map<String, CircularFifoQueue<LocalDateTime>> casualCalls = new HashMap<>();
    // назва методу, кількість запитів за період, кількість помилок.

}
