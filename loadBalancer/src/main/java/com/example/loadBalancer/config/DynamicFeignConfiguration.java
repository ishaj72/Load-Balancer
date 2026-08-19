package com.example.loadBalancer.config;

import com.example.loadBalancer.model.Server;
import com.example.loadBalancer.registry.ServerRegistry;
import com.example.loadBalancer.strategy.LoadBalancingStrategy;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamicFeignConfiguration {

    @Bean
    public RequestInterceptor loadBalancerInterceptor(ServerRegistry serverRegistry,LoadBalancingStrategy loadBalancingStrategy){
        return requestTemplate -> {
            Server server = loadBalancingStrategy.selectServer(serverRegistry.getServers());

            String url = "http://" +
                    server.getHost() +
                    ":" +
                    server.getPort();

            requestTemplate.target(url);
        };

    }
}
