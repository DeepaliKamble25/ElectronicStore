package com.electronic.store.exception;


import com.electronic.store.playload.ApiResponse;
import org.hibernate.hql.internal.classic.AbstractParameterInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptiomHandler {

    public  Logger logger= LoggerFactory.getLogger(GlobalExceptiomHandler.class);
   @ExceptionHandler
    public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException ex){

     logger.info("Exception  Handler  invoked");
       ApiResponse response = ApiResponse.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();

       return new ResponseEntity<>(response,HttpStatus.NOT_FOUND) ;


   }
}
