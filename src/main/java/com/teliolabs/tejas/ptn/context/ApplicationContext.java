package com.teliolabs.tejas.ptn.context;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class ApplicationContext {
    private AuthContext authContext;
    

}
