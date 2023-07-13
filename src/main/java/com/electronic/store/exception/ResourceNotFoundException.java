package com.electronic.store.exception;

import lombok.Builder;

@Builder
public class ResourceNotFoundException extends RuntimeException{



    public ResourceNotFoundException(){
        super("Resource not exception !!!");
    }
    public ResourceNotFoundException(String message){
        super(message);
    }

}
