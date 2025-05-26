package com.teliolabs.tejas.ptn.config;

import lombok.Data;

import java.util.List;

@Data
public class NetworkManagerConfig {
    private String name;
    private String host;
    private Authentication authentication;
    private List<Endpoint> endpoints;
}
