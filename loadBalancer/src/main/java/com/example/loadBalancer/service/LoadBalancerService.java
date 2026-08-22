package com.example.loadBalancer.service;

import com.example.loadBalancer.client.BackendClient;
import com.example.loadBalancer.model.Server;
import com.example.loadBalancer.registry.ServerRegistry;
import com.example.loadBalancer.strategy.LoadBalancingStrategy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class LoadBalancerService {
    /*
    This service class connects registry to loadBalancing strategy.
    Since right now values are hardcoded. I will be calling all server directly using serverRegistry
    class.

    Server server = loadBalancingStrategy.selectServer(serverRegistry.getServers());
        String url = "http://"+server.getHost()+":"+server.getPort()+"/hello";
        return restClient.get().uri(url).retrieve().body(String.class);

        this is the structure that i used for RestClient but as of now i will use Feign client
     */
    private final ServerRegistry serverRegistry;
    private final LoadBalancingStrategy loadBalancingStrategy;
    private final BackendClient backendClient;

    public LoadBalancerService(ServerRegistry serverRegistry, LoadBalancingStrategy loadBalancingStrategy, BackendClient backendClient) {

        this.serverRegistry = serverRegistry;
        this.loadBalancingStrategy = loadBalancingStrategy;
        this.backendClient = backendClient;
    }

    public Server selectServer() {
        return loadBalancingStrategy.selectServer(serverRegistry.getServers());
    }

    public String forwardRequest() {
        return backendClient.hello();
    }

}
