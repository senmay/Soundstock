package com.soundstock.exceptions;

public class ErrorMessages {
    private ErrorMessages() {
    }
    public static final String USERNAME_OR_EMAIL_EXISTS = "Username or Email already exists";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String STOCK_NOT_FOUND = "Stock not found";
    public static final String ORDER_NOT_FOUND = "Order not found";
    public static final String INCORRECT_CREDENTIALS = "Incorrect password";
    public static final String INSUFFICIENT_BALANCE = "User has insufficient balance";
    public static final String ACCESS_DENIED = "User does not have access to this order";
    public static final String SONG_NOT_FOUND = "Song doesn't exist in database";
    public static final String SONG_EXISTS = "Song already exists";

}

