package com.itrach.cbinterceptor.demo.controller;

import com.itrach.cbinterceptor.config.Config;
import com.itrach.cbinterceptor.demo.dto.ConfigDTO;
import com.itrach.cbinterceptor.demo.service.ConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/configs")
@RequiredArgsConstructor
public class ConfigController {

    private final ConfigService configService;

    @GetMapping
    public ResponseEntity<ConfigDTO> getConfigs() {
        return new ResponseEntity<>(configService.getConfig(), HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<String> changeConfigs(@RequestBody ConfigDTO configDTO) {
        configService.updateConfigs(configDTO);
        return new ResponseEntity<>("OK", HttpStatus.ACCEPTED);
    }

}
