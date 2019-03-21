package com.itrach.cbinterceptor.component;

import com.itrach.cbinterceptor.model.MethodMetaStorage;
import com.itrach.cbinterceptor.model.UserMetaStorage;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
public class CallMetaStorage {

    // список методів
    // кількість викликів від одного користувача за період
    private Map<String, UserMetaStorage> userMetaStorageMap;
    private Map<String, MethodMetaStorage> methodMetaStorageMap;


    public void saveUserCall(String ip, UserMetaStorage userMetaStorage) {

    }
}
