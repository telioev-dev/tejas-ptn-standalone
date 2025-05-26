package com.teliolabs.tejas.ptn.exception;


import lombok.Getter;

@Getter
public class NotAvailableException extends ApiException {
    private String body;
    private String message;

    public NotAvailableException(String message, String body) {
        super(message, body);
        this.message = message;
        this.body = body;
    }
}
