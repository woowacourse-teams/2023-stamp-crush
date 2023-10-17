package com.stampcrush.backend.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckApiController {

    @GetMapping("/health-check")
    public String getHealthCheck() {
        return "up";
    }
}
