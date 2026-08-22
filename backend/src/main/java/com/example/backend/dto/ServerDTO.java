package com.example.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ServerDTO {

    private String host;
    private Integer port;

    public ServerDTO(String host , Integer port){
        this.host = host;
        this.port =port;
    }
}
