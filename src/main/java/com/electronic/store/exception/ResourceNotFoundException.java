package com.electronic.store.exception;

import lombok.Builder;

@Builder
public class ResourceNotFoundException extends RuntimeException{



    public ResourceNotFoundException(){
        super("Resource nnot exception !!!");
    }
    public ResourceNotFoundException(String message){
        super(message);
    }

}
