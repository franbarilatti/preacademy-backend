package com.preacademy.second_evaluation.exceptions;


public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message){
        super(message);
    }
}
