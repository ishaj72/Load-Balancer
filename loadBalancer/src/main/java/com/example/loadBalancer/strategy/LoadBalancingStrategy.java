package com.example.loadBalancer.strategy;

import com.example.loadBalancer.model.Server;
import java.util.List;

public interface LoadBalancingStrategy{
    /*
    This class is an iterface.
    This contains select server which can be overwritten by different loadBalancer according to strategy
    to select server.
     */
    Server selectServer(List<Server> serverList);
}
