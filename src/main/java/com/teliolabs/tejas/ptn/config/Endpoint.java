package com.teliolabs.tejas.ptn.config;

import lombok.Data;

@Data
public class Endpoint {
    private String name;
    private String host;
    private String path;
    private String method;
}
