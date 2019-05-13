package com.itrach.cbinterceptor.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Data
@Configuration
public class Config implements Serializable {

    private static final long serialVersionUID = 7858120860755775996L;

    private int userRequestsLimitPerMethod = 3;
    private int userRequestsLimitTotal = 5;
    private int userSecondsPerRequestsLimit = 20;
    private int userCooldownSeconds = 30;
    private int userErrorCooldownSeconds = 30;
    private int userErrorsCapacity = 5;

    private int methodErrorCooldownSeconds = 10;
    private int methodErrorsCapacity = 2;
    private int methodTimeoutSeconds = 3;

}
