package com.itrach.cbinterceptor.model;

import lombok.Data;

import java.util.Map;

@Data
public class UserMetaStorage {

    private String ip;
    // ключем є назва методу із конкретними параметрами. якщо метод валиться то блокуємо метод тільки з цими параметрами
    private Map<String, UserCallMethodMetadata> userCalls;
    // назва методу, кількість запитів за період, кількість помилок.
}
