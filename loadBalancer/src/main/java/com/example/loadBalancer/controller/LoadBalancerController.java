package com.example.loadBalancer.controller;

import com.example.loadBalancer.model.Server;
import com.example.loadBalancer.registry.ServerRegistry;
import com.example.loadBalancer.service.LoadBalancerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(name = "/registry")
public class LoadBalancerController {

    private final LoadBalancerService loadBalancerService;
    private final ServerRegistry serverRegistry;

    public LoadBalancerController(
            LoadBalancerService loadBalancerService,
            ServerRegistry serverRegistry) {

        this.loadBalancerService = loadBalancerService;
        this.serverRegistry = serverRegistry;
    }

    @PostMapping("/register")
    public String register(@RequestBody Server server) {

        serverRegistry.serverRegistry(server);

        return "Server registered successfully";
    }

    @GetMapping("/hello")
    public String hello() {
        return loadBalancerService.forwardRequest();
    }

    @GetMapping("/servers")
    public List<Server> getServers() {
        return serverRegistry.getServers();
    }

    @DeleteMapping("/deregister")
    public String deleteServers(@RequestBody Server server){
        serverRegistry.deleteRegistry(server);
        return server.getPort()+ " is de-registered successfully";
    }

}