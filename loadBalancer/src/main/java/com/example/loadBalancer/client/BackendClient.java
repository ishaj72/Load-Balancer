package com.example.loadBalancer.client;

import com.example.loadBalancer.config.DynamicFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="backendClient", url = "http://dummy",configuration = DynamicFeignConfiguration.class)
public interface BackendClient {

    @GetMapping("/hello")
    String hello();
}


