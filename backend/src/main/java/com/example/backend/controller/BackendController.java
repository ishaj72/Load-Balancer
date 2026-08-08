package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BackendController {

    @Value("${server.port}")
    private int port;

    @GetMapping("/hello")
    public String hello(){
        return "Hello everyone. Welcome to my backend service. "+port;
    }
}
