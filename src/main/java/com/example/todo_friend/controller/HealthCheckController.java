package com.example.todo_friend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping("/health")
    public String healthCheck() {
        return "UP";
    }
    @GetMapping("/friends/version")
    public String version() {
        return "v1.0.1";
    }
}
