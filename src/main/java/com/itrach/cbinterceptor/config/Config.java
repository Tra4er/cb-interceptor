package com.itrach.cbinterceptor.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class Config {

    private int userRequestsLimitPerMethod = 3;
    private int userRequestsLimitTotal = 5;
    private int userSecondsPerRequestsLimit = 20;
    private int userCooldownSeconds = 30;
    private int userErrorCooldownSeconds = 30;
    private int userErrorsCapacity = 5;

    private int methodErrorCooldownSeconds = 10;
    private int methodErrorsCapacity = 2;

}
