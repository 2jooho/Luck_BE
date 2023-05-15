package com.example.luck_project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public String healtCheck() {
        return "OK!";
    }

    @PostMapping("/post/health")
    public String postHealtCheck() {
        return "OK!";
    }
}