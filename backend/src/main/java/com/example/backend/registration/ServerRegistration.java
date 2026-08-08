package com.example.backend.registration;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

@Component
public class ServerRegistration {

    private final Environment environment;

    private  final RestClient restClient = RestClient.create();

    @Value("${loadbalancer.url}")
    private String loadBalancerUrl;

    public ServerRegistration(Environment environment) {
        this.environment = environment;
    }


    @EventListener(ApplicationReadyEvent.class)
    public void register() {

        int port = Integer.parseInt(
                environment.getProperty("server.port")
        );
        restClient.post()
                .uri(loadBalancerUrl + "/registry/register")
                .body(Map.of(
                        "host", "localhost",
                        "port", port
                ))
                .retrieve()
                .toBodilessEntity();
    }
}


