package com.teliolabs.tejas.ptn.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CPOException extends Exception{

    private static final long serialVersionUID = 1L;

    public CPOException(String message){
        super(message);
    }
}
