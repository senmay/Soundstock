package com.soundstock.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.soundstock.exceptions.ErrorMessages.INCORRECT_CREDENTIALS;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex){

        log.info(INCORRECT_CREDENTIALS);
        return new ResponseEntity<>(INCORRECT_CREDENTIALS,HttpStatus.UNAUTHORIZED);
    }

}
