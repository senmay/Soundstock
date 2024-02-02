package com.soundstock.exceptions;

public class ExpiredDate extends RuntimeException {
    public ExpiredDate(String message) {
        super(message);
    }
}
