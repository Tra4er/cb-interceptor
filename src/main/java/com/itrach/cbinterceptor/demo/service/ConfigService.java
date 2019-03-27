package com.itrach.cbinterceptor.demo.service;

import com.itrach.cbinterceptor.config.Config;
import com.itrach.cbinterceptor.demo.dto.ConfigDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigService {

    private final Config config;

    public ConfigDTO getConfig() {
        ConfigDTO configDTO = new ConfigDTO();
        configDTO.setUserCooldownSeconds(config.getUserCooldownSeconds());
        configDTO.setUserErrorCooldownSeconds(config.getUserErrorCooldownSeconds());
        configDTO.setUserErrorsCapacity(config.getUserErrorsCapacity());
        configDTO.setUserRequestsLimitPerMethod(config.getUserRequestsLimitPerMethod());
        configDTO.setUserRequestsLimitTotal(config.getUserRequestsLimitTotal());
        configDTO.setUserSecondsPerRequestsLimit(config.getUserSecondsPerRequestsLimit());

        configDTO.setMethodErrorCooldownSeconds(config.getMethodErrorCooldownSeconds());
        configDTO.setMethodErrorsCapacity(config.getMethodErrorsCapacity());
        return configDTO;
    }

    public void updateConfigs(ConfigDTO configDTO) {
        config.setUserCooldownSeconds(configDTO.getUserCooldownSeconds());
        config.setUserErrorCooldownSeconds(configDTO.getUserErrorCooldownSeconds());
        config.setUserErrorsCapacity(configDTO.getUserErrorsCapacity());
        config.setUserRequestsLimitPerMethod(configDTO.getUserRequestsLimitPerMethod());
        config.setUserRequestsLimitTotal(configDTO.getUserRequestsLimitTotal());
        config.setUserSecondsPerRequestsLimit(configDTO.getUserSecondsPerRequestsLimit());

        config.setMethodErrorCooldownSeconds(configDTO.getMethodErrorCooldownSeconds());
        config.setMethodErrorsCapacity(configDTO.getMethodErrorsCapacity());
    }
}
