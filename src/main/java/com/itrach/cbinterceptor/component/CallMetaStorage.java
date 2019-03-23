package com.itrach.cbinterceptor.component;

import com.itrach.cbinterceptor.model.method.MethodMetaStorage;
import com.itrach.cbinterceptor.model.user.UserMetaStorage;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
public class CallMetaStorage {

    // список методів
    // кількість викликів від одного користувача за період
    private Map<String, UserMetaStorage> userMetaStorageMap = new HashMap<>();
    private Map<String, MethodMetaStorage> methodMetaStorageMap = new HashMap<>();

    public UserMetaStorage getUserByIp(String ip) {
        return userMetaStorageMap.get(ip);
    }

    public void saveUser(UserMetaStorage userMetaStorage) {
        userMetaStorageMap.put(userMetaStorage.getIp(), userMetaStorage);
    }

}
