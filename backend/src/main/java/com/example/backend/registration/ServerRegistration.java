package com.example.backend.registration;

import com.example.backend.dto.ServerDTO;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

@Component
public class ServerRegistration {

    private Environment environment;

    private  final RestClient restClient = RestClient.create();

    public ServerRegistration(Environment environment) {
        this.environment = environment;
    }

    @Value("${loadbalancer.url}")
    private String loadBalancerUrl;

    @Value("${server.address}")
    private String serverHost;

    @Value("${server.port}")
    private Integer port;

    @EventListener(ApplicationReadyEvent.class)
    public void register() {


        restClient.post()
                .uri(loadBalancerUrl + "/registry/register")
                .body(Map.of(
                        "host", serverHost,
                        "port", port
                ))
                .retrieve()
                .toBodilessEntity();
    }

    /*
    Since restclient.delete was not working i have use method (HttpMethod.DELETE) because delete does not support body function
     */
    @PreDestroy
    public void deregisteration(){
        restClient.method(HttpMethod.DELETE)
                .uri(loadBalancerUrl + "/registry/deregister")
                .body(new ServerDTO(serverHost,port)).retrieve().toBodilessEntity();
    }
}


