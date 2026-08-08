package com.example.loadBalancer.registry;

import com.example.loadBalancer.model.Server;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServerRegistry {
    /*
    This class will store backend server.
     */
    private final List<Server> servers = new ArrayList<>();
    public void serverRegistry(Server server){
        boolean alreadyRegistered = servers.stream()
                .anyMatch(existing ->
                        existing.getHost().equals(server.getHost())
                                && existing.getPort()==(server.getPort())
                );

        if (!alreadyRegistered) {
            servers.add(server);
        }
    }
    public List<Server> getServers(){
        return servers; // maintains the list of all servers
    }

}


