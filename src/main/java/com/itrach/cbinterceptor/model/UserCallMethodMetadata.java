package com.itrach.cbinterceptor.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;

@Data
public class UserCallMethodMetadata {

    private Queue<LocalDateTime> myQ = new LinkedList<>();
    private int errorsCount;

}
