package com.example.loadBalancer.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Server {

    /*
    This class will help create object of server with the below attributes.
    This is not an entity it simply a model(no primary key
     */

    private String host;
    private Integer port;

    public Server(){};

    public Server(String host,Integer port){
        this.host = host;
        this.port = port;
    }



    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
