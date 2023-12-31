package com.electronic.store.exception;


import com.electronic.store.playload.ApiResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptiomHandler {

    public  Logger logger= LoggerFactory.getLogger(GlobalExceptiomHandler.class);
//MethodArgumentNotValidException



    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException ex){

     logger.info("ResourceNotFoundException  Handler  invoked");
       ApiResponse response = ApiResponse.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();

       return new ResponseEntity<>(response,HttpStatus.NOT_FOUND) ;


   }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> userNotFoundException(UserNotFoundException ex){

        logger.info("User Exception  Handler  invoked");
        ApiResponse response = ApiResponse.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();

        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND) ;


    }
//    MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleMethodeArgumentNotValidException(MethodArgumentNotValidException ex){
        logger.info("MethodArgumentNotValidException  Handler  invoked");
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        Map<String,Object> response=new HashMap<>();
        allErrors.stream().forEach(objectError ->
        {
            String defaultMessage = objectError.getDefaultMessage();
            String field = ((FieldError) objectError).getField();
            response.put(field,defaultMessage);
        });
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<ApiResponse> badApiRequest(BadApiRequest ex){

        logger.info("BadApiRequest Exception  Handler  invoked");
        ApiResponse response = ApiResponse.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(false).build();

        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND) ;
    }



}
