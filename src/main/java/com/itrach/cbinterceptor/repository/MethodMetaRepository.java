package com.itrach.cbinterceptor.repository;

import com.itrach.cbinterceptor.config.Config;
import com.itrach.cbinterceptor.model.UserMetadata;
import lombok.Data;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@Repository
public class MethodMetaRepository {

    // список методів
    // кількість викликів від одного користувача за період
    private Map<String, CircularFifoQueue<LocalDateTime>> methodErrors = new HashMap<>();

    public CircularFifoQueue<LocalDateTime> getMethodMetaByName(String name) {
        return methodErrors.get(name);
    }

    public void save(String method, CircularFifoQueue<LocalDateTime> errors) {
        methodErrors.put(method, errors);
    }

}
