package com.itrach.cbinterceptor.demo.dto;

import lombok.Data;

@Data
public class ConfigDTO {

    private int userRequestsLimitPerMethod;
    private int userRequestsLimitTotal;
    private int userSecondsPerRequestsLimit;
    private int userCooldownSeconds;
    private int userErrorCooldownSeconds;
    private int userErrorsCapacity;

    private int methodErrorCooldownSeconds;
    private int methodErrorsCapacity;
    private int methodTimeoutSeconds = 1;

}
