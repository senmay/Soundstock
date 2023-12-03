package com.soundstock.exceptions;

public class ErrorMessages {
    private ErrorMessages() {
    }
    public static final String ENTITY_EXISTS = "Username or Email already exists";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String STOCK_NOT_FOUND = "Stock not found";
    public static final String ORDER_NOT_FOUND = "Order not found";
    public static final String INCORRECT_CREDENTIALS = "Incorrect username or password";
    public static final String INSUFFICIENT_BALANCE = "User has insufficient balance";
    public static final String ACCESS_DENIED = "User does not have access to this order";

}

