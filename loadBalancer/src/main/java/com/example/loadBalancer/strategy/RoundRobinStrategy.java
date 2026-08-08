package com.example.loadBalancer.strategy;

import com.example.loadBalancer.model.Server;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RoundRobinStrategy implements LoadBalancingStrategy{
    /*
        This class implements Round Robin strategy
        AtomicInteger helps handeling thread safe counter
        index -> this is main calculation here to find the server that comes first
        counter = 0 -> index = 0%3 -> server 0 gets chosen and counter gets increased to 1 and son on..
     */
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Server selectServer(List<Server> serverList){
        if(serverList == null || ObjectUtils.isEmpty(serverList)){
            throw new IllegalStateException("No Server found");
        }
        int index = counter.getAndIncrement()%serverList.size();
        return serverList.get(index);
    }
}

