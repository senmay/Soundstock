package com.soundstock.exceptions;

public class ObjectNotFound extends RuntimeException{
    private Class<?> sourceClass;
    public ObjectNotFound(Class<?> sourceClass){
        this.sourceClass = sourceClass;
    }
    public ObjectNotFound(String message){
        super(message);
    }
    public ObjectNotFound(String message, Class<?> sourceClass){
        super(message);
        this.sourceClass = sourceClass;
    }
    public ObjectNotFound(String message, Throwable cause, Class<?> sourceClass){
        super(message, cause);
        this.sourceClass = sourceClass;
    }
    @Override
    public String getMessage() {
        String sourceClassName = sourceClass != null ? sourceClass.getSimpleName() : "Unknown";
        return "Exception thrown in class: " + sourceClassName + ". " + super.getMessage();
    }
}
