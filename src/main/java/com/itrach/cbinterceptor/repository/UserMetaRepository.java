package com.itrach.cbinterceptor.repository;

import com.itrach.cbinterceptor.model.UserMetadata;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserMetaRepository {

    private Map<String, UserMetadata> userMetaStorageMap = new HashMap<>();

    public UserMetadata getUserByIp(String ip) {
        return userMetaStorageMap.get(ip);
    }

    public void saveUser(UserMetadata userMetaStorage) {
        userMetaStorageMap.put(userMetaStorage.getIp(), userMetaStorage);
    }

}
